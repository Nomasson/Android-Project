package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class DispatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);

        Class <?> activityClass;

        SharedPreferences preferences = getSharedPreferences(MainActivity.sharedFile,MODE_PRIVATE);
        try {
            activityClass = Class.forName(preferences.getString(MainActivity.lastActivityKeyName,MainActivity.class.getName()));
        } catch (ClassNotFoundException e) {
            activityClass = MainActivity.class;
        }

        startActivity(new Intent(this,activityClass));
    }
}
