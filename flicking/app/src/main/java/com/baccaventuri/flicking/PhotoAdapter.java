package com.baccaventuri.flicking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baccaventuri.flicking.Models.Comment;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private List<Comment> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    PhotoAdapter(Context context, List<Comment> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // stores and recycles views as they are scrolled off screen
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView autor;
        TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            autor = itemView.findViewById(R.id.comment_item_name);
            content = itemView.findViewById(R.id.comment_item_content);
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public PhotoAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.comment_item, parent, false);
        return new MyViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment comment = mData.get(position);
        holder.autor.setText(comment.getAuthorname());
        holder.content.setText(comment.getContent());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    public void updateDataset (List<Comment> mData) {
        this.mData = mData;
    }

    public Comment getItem (int pos) {
        return mData.get(pos);
    }

}