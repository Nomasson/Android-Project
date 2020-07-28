package com.example.finalproject.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalproject.HistoryActivity;
import com.example.finalproject.MyDatabaseContract;
import com.example.finalproject.R;
import com.example.finalproject.model.History;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList history_list;
    MediaPlayer p;

    public HistoryAdapter(Context context, ArrayList history_list){
        this.context = context;
        this.history_list = history_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.history_card_view,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.history_id.setText(String.valueOf((((History) history_list.get(position)).getHistory_id())));
        holder.history_search.setText(String.valueOf((((History) history_list.get(position)).getHistory_search())));
        holder.history_map.loadUrl(String.valueOf((((History) history_list.get(position)).getHistory_map())));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p == null){
                    p = MediaPlayer.create(v.getContext(),R.raw.delete);
                }
                p.start();
                if(getItemCount() == 1){
                    deleteHistory(0);
                    notifyItemRemoved(0);
                    return;
                }
                deleteHistory(position);
                notifyItemRemoved(position);

            }
        });



    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button delete;
        TextView history_id, history_search;
        WebView history_map;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.btn_delete);
            history_id = itemView.findViewById(R.id.historyID);
            history_search = itemView.findViewById(R.id.historySearch);
            history_map = itemView.findViewById(R.id.historyMap);
            history_map.setWebViewClient(new WebViewClient());
            WebSettings settings = history_map.getSettings();
            settings.setJavaScriptEnabled(true);
        }
    }

    public void deleteHistory(int value)
    {

        MyDatabaseContract.MyDatabaseHelper myDbHelper = new MyDatabaseContract.MyDatabaseHelper(this.context);
        History toDelete = (History) history_list.get(value);
        String posToDeleteInSQL = toDelete.getHistory_id();
        myDbHelper.deleteEntry(posToDeleteInSQL);
        Log.i("sqlite",value+"");
        history_list.remove(value);



    }

}
