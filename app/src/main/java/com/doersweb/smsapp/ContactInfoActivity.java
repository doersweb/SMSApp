package com.doersweb.smsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
* This is the activity that displays the contact name and number passed in intent that started this activity
* */

import java.util.Random;

import data.Data;

public class ContactInfoActivity extends AppCompatActivity {

    int random_number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        //Setting the title of the acitivity
        this.setTitle("Contact Info");

        //Capturing view from the layout
        TextView name = (TextView) findViewById(R.id.name);
        TextView mobileNumber = (TextView) findViewById(R.id.mobileNum);
        Button sendMessage = (Button) findViewById(R.id.sendMessage);

        //Getting the intent that started this activity to extract the data passed
        Intent intent = getIntent();

        name.setText(intent.getStringExtra(Data.FIRST_NAME) + " " + intent.getStringExtra(Data.LAST_NAME));
        mobileNumber.setText(intent.getStringExtra(Data.MOB_NUMBERS));


        //This is to generate a random number
        Random random = new Random();

        //This is to make sure that the generated number is of 6-digit
        random_number = (100000 + random.nextInt(900000));


        //To handle click on the button
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent to start another activity
                Intent intent = new Intent(ContactInfoActivity.this, SendingSmsActivity.class);
                intent.putExtra(Data.FIRST_NAME, getIntent().getStringExtra(Data.FIRST_NAME));
                intent.putExtra(Data.LAST_NAME, getIntent().getStringExtra(Data.LAST_NAME));
                intent.putExtra(Data.MOB_NUMBERS, getIntent().getStringExtra(Data.MOB_NUMBERS));
                intent.putExtra(Data.OTP, random_number);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        //This is to clear the top of backstack to make sure our MainActivity's backpress
        //doesn't lead to switching back to any other activity on stack.
        this.finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();

    }
}
