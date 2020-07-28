package com.example.finalproject.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.finalproject.model.Item;
import com.example.finalproject.model.Owner;
import com.example.finalproject.R;
import com.example.finalproject.model.Item;
import com.example.finalproject.model.Owner;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{

    private List<Item> itemResult;
    MediaPlayer player;

    public ItemAdapter(List<Item> itemResult){
        this.itemResult = itemResult;

    }
    @NonNull
    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_card_view,parent,false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ItemViewHolder holder, int position) {
        Item item = itemResult.get(position);
        Owner o = item.getOwner();
        holder.itemTitle.setText(item.getTitle());
        holder.itemLink.setText(item.getLink());
        Glide.with(holder.itemView.getContext())
                .load(item.getOwner().getProfileImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.ownerProfileImage);

    }

    @Override
    public int getItemCount() {
        return this.itemResult.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView itemTitle;
        private TextView itemLink;
        private ImageView ownerProfileImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.history_id);
            itemLink = itemView.findViewById(R.id.history_search);
            ownerProfileImage = itemView.findViewById(R.id.item_poster);
        }
    }
}
