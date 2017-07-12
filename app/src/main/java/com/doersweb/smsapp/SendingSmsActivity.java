package com.doersweb.smsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import data.Data;
import util.ContactsDataBaseHelper;


/*
* This activity displays the message text with a 6-digit OTP number that was passed in intent for use
* in this activity.
*
* It sends the message to the selected contact from the MainActivity's contact list
* */

public class SendingSmsActivity extends AppCompatActivity {


    String mobileNumber, name;
    int otp;
    TextView smsContent;
    ProgressBar progressBar;

    ContactsDataBaseHelper contactsDataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_sms);

        //Creating ContactsDataBaseHelpern object to insert sms sent details in the database
        contactsDataBaseHelper = new ContactsDataBaseHelper(this);

        //Getting the intent that started this activity to extract the data passed
        Intent intent = getIntent();

        //Capturing view's from the layout
        smsContent = (TextView) findViewById(R.id.smsContent);
        Button sendSms = (Button) findViewById(R.id.sendSms);
        progressBar = (ProgressBar) findViewById(R.id.progress);


        otp = intent.getIntExtra(Data.OTP, 0);
        mobileNumber = intent.getStringExtra(Data.MOB_NUMBERS);
        name = intent.getStringExtra(Data.FIRST_NAME) + " " + getIntent().getStringExtra(Data.LAST_NAME);

        //Setting text in the textview
        smsContent.setText("Hi, Your OTP is " + otp);


        //To handle click on the button
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Method to make http call to send the sms using Volley library
                shootSms();
            }
        });
    }


    //Sending sms via an HTTP call using Volley library
    private void shootSms() {


        progressBar.setVisibility(View.VISIBLE);
        //Creating hashmap of the required parameters to eventually create our json for
        //the api call.
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("api_key", Data.NEXMO_API_KEY);
        params.put("api_secret", Data.NEXMO_API_SECRET);
        params.put("to", "91" + mobileNumber);
        params.put("from", Data.SENDER_ID);
        params.put("text", smsContent.getText().toString());

        //Creating a volley request queue
        RequestQueue queue = Volley.newRequestQueue(SendingSmsActivity.this);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Data.BASE_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());

                        try {


                            //Parsing the JSON response we get
                            JSONArray jsonArray = response.getJSONArray("messages");
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            if (jsonObject.getString("status").equals("0")) {
                                //When we have successfully sent the sms, we ll the message details in the database
                                addToDatabase(name, otp, mobileNumber);

                            } else {
                                progressBar.setVisibility(View.GONE);

                                //Handling responses in case our message doesn't go through
                                Toast.makeText(SendingSmsActivity.this, jsonObject.getString("error-text"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                //Handling response in case of request results in an error and doesn't get through
                Toast.makeText(SendingSmsActivity.this, error.toString() + ", " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //adding the jsonrequest to the request queue
        queue.add(jsonRequest);

    }


    private void addToDatabase(String name, int otp, String mobileNumber) {

        //This is to get the current time to store in the database
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        String date = sdf.format(new Date());

        //Calling the method in ContactsDataBaseHelper class to insert data into the database
        boolean isDataInserted = contactsDataBaseHelper.insertData(name, otp, date, mobileNumber);


        progressBar.setVisibility(View.GONE);

        //Check whether the data was inserted or not
        if (isDataInserted) {

            Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SendingSmsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Some problem occured, please try again!", Toast.LENGTH_SHORT).show();
        }


    }

}
