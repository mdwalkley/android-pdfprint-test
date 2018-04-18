package com.example.aqndroid.justjava;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;

public class PrintActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);

        findViewById(R.id.print_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                printPDF(view);
            }
        });

        findViewById(R.id.editInfo_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent editInfoIntent = new Intent(PrintActivity.this, EditInfoActivity.class);
                startActivity(editInfoIntent);
            }
        });

    }


    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }


    public static boolean canWriteOnExternalStorage() {
        // get the state of your external storage
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // if storage is mounted return true
            Log.v("sTag", "Yes, can write to external storage.");
            return true;
        }
        return false;
    }

    public void printPDF(View view){
        //Request permission to write to storage.
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        //hide print button
        findViewById(R.id.button_view_layout).setVisibility(View.GONE);

        //create PDF document
        View parentLayout = findViewById(R.id.print_view_layout);
        int pagewidth = parentLayout.getWidth();
        int pageheight = parentLayout.getHeight();
        PdfDocument receipt = new PdfDocument();
        PdfDocument.PageInfo pageInfo= new PdfDocument.PageInfo.Builder(pagewidth, pageheight, 1).create();
        PdfDocument.Page page = receipt.startPage(pageInfo);
        parentLayout.draw(page.getCanvas());
        receipt.finishPage(page);


        canWriteOnExternalStorage();

//https://stackoverflow.com/questions/7887078/android-saving-file-to-external-storage
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File myDir = new File(root + "/PDFs");
        myDir.mkdirs();
        String fileName = "JavaReceipt.pdf";
        File newFile = new File(myDir, fileName);
//

         try{
             receipt.writeTo(new FileOutputStream(newFile));
             Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
         } catch (IOException e){
             e.printStackTrace();
             Toast.makeText(this, "Fuck this error: " + e.toString(), Toast.LENGTH_LONG).show();
         }



//
        MediaScannerConnection.scanFile(this, new String[]{newFile.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String s, Uri uri) {
                Log.i("ExternalStorage", "Scanned " + s + ":");
                Log.i("ExternalStorage", "-> uri=" + uri);
            }
        });
//
        receipt.close();
    }

    //save document
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


}
