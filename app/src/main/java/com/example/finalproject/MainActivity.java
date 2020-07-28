package com.example.finalproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    EditText searchTerm;
    private Button button;
    public static final String sharedFile = "sharedfile";
    public static final String lastActivityKeyName = "lastActivity";
    LocationManager locationManager;
    MyDatabaseContract.MyDatabaseHelper myDbHelper;
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences(sharedFile, 0);
        myDbHelper = new MyDatabaseContract.MyDatabaseHelper(this);
        dbWrite = myDbHelper.getWritableDatabase();
        dbRead = myDbHelper.getReadableDatabase();
        searchTerm = findViewById(R.id.searchTerm);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Please check your location permissions", Toast.LENGTH_SHORT).show();
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3 * 1000, 10, this);

        if (searchTerm != null) {
            searchTerm.setText(sharedPreferences.getString("search", null));

        }

        button = (Button) findViewById(R.id.btn_delete);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                startSearch(v);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startSearch(View view) {
        if (searchTerm.getText().toString().matches("")){
            Toast.makeText(this, "Enter a valid search term", Toast.LENGTH_SHORT).show();
        }
        else {
        // get the last know location from your location manager.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (player == null){
            player = MediaPlayer.create(this,R.raw.search);
        }
        player.start();
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        ContentValues values = new ContentValues();
        String strUrl = "https://www.google.com/maps/place/" + location.getLatitude()+","+location.getLongitude();

        values.put(MyDatabaseContract.TableHistory.COL_NAME_SEARCH,searchTerm.getText().toString());
        values.put(MyDatabaseContract.TableHistory.COL_NAME_MAP_URL,strUrl);
        dbWrite.insert(MyDatabaseContract.TableHistory.TABLE_Name,null,values);

        SharedPreferences sharedPreferences = getSharedPreferences(sharedFile,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("search", searchTerm.getText().toString()).commit();
        Intent intent = new Intent(this, ItemsActivity.class);
        startActivity(intent);
    }}

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(sharedFile,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (searchTerm != null){
            editor.putString("search", searchTerm.getText().toString()).commit();
        }
        editor.putString(lastActivityKeyName, getClass().getName());
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_main)
            return true;

        if (id == R.id.action_items){
            startActivity(new Intent(this, ItemsActivity.class));

        }
        if (id == R.id.action_history){
            startActivity(new Intent(this, HistoryActivity.class));

        }

        if (id == R.id.action_about){
            startActivity(new Intent(this, AboutActivity.class));

        }


        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

