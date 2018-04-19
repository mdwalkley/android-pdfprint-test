package com.example.aqndroid.justjava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        setSavedFields();

        findViewById(R.id.saveInfo_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                saveInfo();
                onBackPressed();
            }
        });
    }

    private void setSavedFields(){
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.SHARED_PREFS_EDIT_INFO), MODE_PRIVATE);
        String str = sharedPref.getString(getString(R.string.sharedPref_companyName_key), "Company Name");
        ((EditText)findViewById(R.id.companyName_editText)).setText(str);
        str = sharedPref.getString(getString(R.string.sharedPref_userName_key), "User Name");
        ((EditText)findViewById(R.id.userName_editText)).setText(str);
    }

    private void saveInfo(){
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.SHARED_PREFS_EDIT_INFO), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.sharedPref_companyName_key), ((EditText)findViewById(R.id.companyName_editText)).getText().toString());
        editor.putString(getString(R.string.sharedPref_userName_key), ((EditText)findViewById(R.id.userName_editText)).getText().toString());
        editor.apply();
    }
}
