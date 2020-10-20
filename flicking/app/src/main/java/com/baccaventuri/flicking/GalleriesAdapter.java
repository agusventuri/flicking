package com.baccaventuri.flicking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baccaventuri.flicking.Data.DataProvider;
import com.baccaventuri.flicking.Models.Gallery;
import com.baccaventuri.flicking.Models.Photoset_;

import java.util.List;

public class GalleriesAdapter extends RecyclerView.Adapter<GalleriesAdapter.MyViewHolder> {
    private List<Photoset_> mData;
    private LayoutInflater mInflater;
    private AlbumClickListener mClickListener;

    // data is passed into the constructor
    GalleriesAdapter(Context context,Gallery gallery, List<Photoset_> data, AlbumClickListener itemClickListener) {
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
            myTextView = itemView.findViewById(R.id.gallery_item_name);
            myImageView = itemView.findViewById(R.id.gallery_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            mClickListener.onAlbumClick(mData.get(getAdapterPosition()));
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public GalleriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gallery_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Photoset_ album = mData.get(position);
        holder.myTextView.setText(album.getTitle());
        holder.myImageView.setImageBitmap(album.getBitmap());
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
    void setClickListener(AlbumClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void updateDataset (List<Photoset_> mData) {
        this.mData = mData;
    }

    public Photoset_ getItem (int pos) {
        return mData.get(pos);
    }

    // parent activity will implement this method to respond to click events
    public interface AlbumClickListener {
        //void onItemClick(View view, int position);
        void onAlbumClick(Photoset_ album);
    }
}