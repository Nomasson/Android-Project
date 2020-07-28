package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        VideoView mp = findViewById(R.id.videoView);
        String videoPath = "android.resource://" +getPackageName()+"/" +R.raw.video;
        Uri uri = Uri.parse(videoPath);
        mp.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mp.setMediaController(mediaController);
        mediaController.setAnchorView(mp);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_about)
            return true;

        if (id == R.id.action_items){
            startActivity(new Intent(this, ItemsActivity.class));
        }
        if (id == R.id.action_main){
            startActivity(new Intent(this, MainActivity.class));
        }

        if (id == R.id.action_history){
            startActivity(new Intent(this, HistoryActivity.class));
        }


        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.sharedFile,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.lastActivityKeyName, getClass().getName());
        editor.commit();
    }


}
