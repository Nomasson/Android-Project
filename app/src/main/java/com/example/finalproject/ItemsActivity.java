package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.finalproject.adapters.ItemAdapter;
import com.example.finalproject.model.Item;
import com.example.finalproject.model.SearchPageResult;
import com.example.finalproject.network.GetItemDataService;
import com.example.finalproject.network.Retrofitinstance;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsActivity extends AppCompatActivity {
    private GetItemDataService itemService;
    List<Item> items;
    RecyclerView rvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        rvItems = findViewById(R.id.recyclerView);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.sharedFile,0);
        itemService = Retrofitinstance.getRetrofitInstance().create(GetItemDataService.class);
        String search = sharedPreferences.getString("search", "");
        itemService.getSearchResult(search).enqueue(new Callback<SearchPageResult>() {
            @Override
            public void onResponse(Call<SearchPageResult> call, Response<SearchPageResult> response) {
                SearchPageResult searchPageResult = response.body();
                items = searchPageResult.getItems();
                if (items.size() < 1){
                    Toast.makeText(getApplicationContext(), "No results found on StackOverflow, try a different search term", Toast.LENGTH_SHORT).show();
                    return;
                }
                ItemAdapter adapter = new ItemAdapter(items);
                rvItems.setAdapter(adapter);
                rvItems.setLayoutManager(new LinearLayoutManager(ItemsActivity.this));
            }

            @Override
            public void onFailure(Call<SearchPageResult> call, Throwable t) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_items)
            return true;

        if (id == R.id.action_main){
            startActivity(new Intent(this, MainActivity.class));
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
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.sharedFile,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.lastActivityKeyName, getClass().getName());
        editor.commit();
    }

}