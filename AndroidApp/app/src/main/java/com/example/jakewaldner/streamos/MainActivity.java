package com.example.jakewaldner.streamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    public static final String TAG = "CancelTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView responseText = (TextView) findViewById(R.id.responseText);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        responseText.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseText.setText("Error:/");
            }
        });

        Button sendButton = (Button) this.findViewById(R.id.sendPost_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add the request to the RequestQueue.
                stringRequest.setTag(TAG);
                requestQueue.add(stringRequest);
            }
        });

        /*Button recieveButton = (Button) this.findViewById(R.id.sendRecieve_button);
        recieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //put http code here
            }
        });*/

        Button cancelButton = (Button) this.findViewById(R.id.cancelRequest_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (requestQueue != null) {
                    requestQueue.cancelAll(TAG);
                    responseText.setText("Request stopped/canceled");
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}
