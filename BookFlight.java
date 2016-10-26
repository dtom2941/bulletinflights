package com.example.bulletinflights;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class BookFlight extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_flight);
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

        goBookDestination();
        goMainMenu();
    }

    /**
     * method for sending the user to the page for booking flights
     */
    public void goBookDestination()
    {
        Button goScheduleDestinationFlight= (Button)findViewById(R.id.bookDestinationFlight);
        goScheduleDestinationFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(BookFlight.this, BookDestinationFlight.class);
                startActivity(go);
            }
        });
    }

    /**
     * method for sending the user to the page for booking flights
     */
    public void goMainMenu()
    {
        Button goMainToMenu= (Button)findViewById(R.id.MainMenuBookFlight);
        goMainToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent go = new Intent(BookFlight.this, MainMenu.class);
                startActivity(go);
            }
        });
    }
}
