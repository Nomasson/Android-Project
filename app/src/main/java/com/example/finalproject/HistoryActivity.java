package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproject.adapters.HistoryAdapter;
import com.example.finalproject.model.History;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryActivity extends AppCompatActivity {
    private static MyDatabaseContract.MyDatabaseHelper myDB;
    private static SQLiteDatabase dbWrite;
    RecyclerView recyclerView;
    static ArrayList<History> history_list;
    HistoryAdapter historyAdapter;
    Button btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerView);
        myDB = new MyDatabaseContract.MyDatabaseHelper((HistoryActivity.this));
        dbWrite = myDB.getWritableDatabase();
        history_list = new ArrayList<>();
        btn = findViewById(R.id.btn_delete);

        storeDataInArray();
        historyAdapter = new HistoryAdapter(HistoryActivity.this,history_list);
        recyclerView.setAdapter(historyAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));




    }



    void storeDataInArray(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data in db.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                History newHistory = new History(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                history_list.add(newHistory);

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_history)
            return true;

        if (id == R.id.action_items){
            startActivity(new Intent(this, ItemsActivity.class));
        }
        if (id == R.id.action_main){
            startActivity(new Intent(this, MainActivity.class));
        }

        if (id == R.id.action_about){
            startActivity(new Intent(this, AboutActivity.class));
        }


        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data in db.", Toast.LENGTH_SHORT).show();
        }
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.sharedFile,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.lastActivityKeyName, getClass().getName());
        editor.commit();
    }

    }
