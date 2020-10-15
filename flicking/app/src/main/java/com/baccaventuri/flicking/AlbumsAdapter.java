package com.baccaventuri.flicking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baccaventuri.flicking.Models.Photo;

import java.util.List;

import static android.content.ContentValues.TAG;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private List<Photo> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    AlbumsAdapter(Context context, List<Photo> data, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        setClickListener(itemClickListener);
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView myImageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.album_item_name);
            myImageView = itemView.findViewById(R.id.album_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            mClickListener.onItemClick(mData.get(getAdapterPosition()));
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public AlbumsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.album_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photo photo = mData.get(position);
        holder.myTextView.setText(photo.getTitle());
        holder.myImageView.setImageBitmap(photo.getBitmap());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void updateDataset (List<Photo> mData) {
        this.mData = mData;
    }

    public Photo getItem (int pos) {
        return mData.get(pos);
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        //void onItemClick(View view, int position);
        void onItemClick(Photo photo);
    }
}