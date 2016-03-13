package com.meleeChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


public class Menu extends AppCompatActivity{
    private static final String LOG_TAG = "MENU";
    Bundle b;

    @Override
    protected void onResume() {
        getSupportActionBar().setTitle("Main Menu");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    public void alertPlayersButton(View v) {
        Intent intent = new Intent(this, AlertList.class);

        intent.putExtras(b); //pass lat and lon along

        startActivity(intent);
    }

    public void reportResultsButton(View v) {
        Log.i(LOG_TAG, "NOTHINGGG");
    }

    public void displayInfoButton(View v) {
        Log.i(LOG_TAG, "NOTHINGGG2");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        b = getIntent().getExtras(); //get lat an lon to pass to other activities

    }
}
