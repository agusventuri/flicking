package com.baccaventuri.flicking;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baccaventuri.flicking.Data.PhotoProvider;
import com.baccaventuri.flicking.Models.Photo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {
    private List<Photo> mData;
    private LayoutInflater mInflater;
    private PhotoClickListener mClickListener;

    // data is passed into the constructor
    AlbumsAdapter(Context context, List<Photo> data, PhotoClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        setClickListener(itemClickListener);
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView myDateView;
        ImageView myImageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.album_item_name);
            myDateView = itemView.findViewById(R.id.album_item_date);
            myImageView = itemView.findViewById(R.id.album_item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            mClickListener.onPhotoClick(mData.get(getAdapterPosition()));
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
        holder.myDateView.setText(photo.getTakenString());
        holder.myImageView.setImageBitmap(photo.getBitmap());
    }

//    public void onClickShowDetails(View view) {
//        // Retrieve employee records
//        TextView resultView = (TextView) findViewById(R.id.res);
//        Cursor cursor = getContentResolver().query(Uri.parse("content://com.tdam.contentprovider.UserProvider/users"), null, null, null, null);
//        if (cursor.moveToFirst()) {
//            StringBuilder strBuild = new StringBuilder();
//            while (!cursor.isAfterLast()) {
//                strBuild.append("\n" + cursor.getString(cursor.getColumnIndex("id")) + "-" + cursor.getString(cursor.getColumnIndex("name")));
//                cursor.moveToNext();
//            }
//            resultView.setText(strBuild);
//        } else {
//            resultView.setText("No Records Found");
//        }
//    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    // allows clicks events to be caught
    void setClickListener(PhotoClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public void updateDataset (List<Photo> mData) {
        this.mData = mData;
    }

    public Photo getItem (int pos) {
        return mData.get(pos);
    }

    // parent activity will implement this method to respond to click events
    public interface PhotoClickListener {
        //void onItemClick(View view, int position);
        void onPhotoClick(Photo photo);
    }
}