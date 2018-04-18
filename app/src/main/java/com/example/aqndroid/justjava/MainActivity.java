package com.example.aqndroid.justjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {
    //Global Constants
    static final int PRICE = 5;
    //Global Variables
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view){
        display(++quantity);
        hidePrintButton();
    }

    public void decrement(View view){
        display((quantity < 1) ? quantity : --quantity);
        hidePrintButton();
    }

    public void submitOrder(View view){
        String total = NumberFormat.getCurrencyInstance().format(PRICE*quantity);
        String priceMessage = "Total: " + total + "\nThank You!";
        displayMessage(priceMessage);
    }

    public void printReceipt(View view){
        Intent printIntent = new Intent(MainActivity.this, PrintActivity.class);
        printIntent.putExtra("ORDER_QUANTITY", quantity);
        printIntent.putExtra("ORDER_PRICE", PRICE );
        startActivity(printIntent);
    }

    private void display(int num){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""+ num);
    }

    private void displayPrice(int num){
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(num));
    }

    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);
        showPrintButton();
    }

    private void showPrintButton(){
       Button printButton = findViewById(R.id.print_button);
       printButton.setVisibility(View.VISIBLE);
    }

    private void hidePrintButton(){
        Button printButton = findViewById(R.id.print_button);
        printButton.setVisibility(View.GONE);
    }

}
