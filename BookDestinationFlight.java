package com.example.bulletinflights;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.net.*;
import java.io.BufferedInputStream;

public class BookDestinationFlight extends AppCompatActivity {

    public final EditText startText = (EditText)findViewById(R.id.startLocationPicker);
    public final EditText endText = (EditText)findViewById(R.id.endLocationPicker);
    public final EditText timeText = (EditText)findViewById(R.id.timePicker);
    public final EditText dateText = (EditText)findViewById(R.id.datePicker);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_destination_flight);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        postFlight();
    }

    public void postFlight()
    {
        Button goBookFlight= (Button)findViewById(R.id.ButtonGoBookflight);

        goBookFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String urlString =
                        String.format("http://bulletinflights.com/db_post_flights.php?time="+
                                "%s&date=%s&starting_l"+
                                "ocation=%s&ending_location=%s",
                                timeText.getText().toString(), dateText.getText().toString(),
                                startText.getText().toString(), endText.getText().toString());
                try {
                    URL url = new URL(urlString);

                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                    new BufferedInputStream(urlConnection.getInputStream());
                }
                catch(Exception e){}
                Intent go = new Intent(BookDestinationFlight.this, MainMenu.class);
                startActivity(go);
            }
        });
    }
}