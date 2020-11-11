package com.baccaventuri.flicking;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ItemsAlbumesAdapter extends
        RecyclerView.Adapter<ItemsAlbumesAdapter.ViewHolder> {

    public TextView name;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View itemAlbumView = inflater.inflate(R.layout.album_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(itemAlbumView);
        name = viewHolder.itemView.findViewById(R.id.album_item_name);

        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ItemsAlbumesAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        ItemAlbumes item = mItems.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(item.getName());

        ImageView imageView = holder.image;
        imageView.setImageURI(Uri.parse("data:image/gif;base64,R0lGODlhEAAQAMQAAORHHOVSKudfOulrSOp3WOyDZu6QdvCchPGolfO0o/XBs/fNwfjZ0frl3/zy7////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAABAALAAAAAAQABAAAAVVICSOZGlCQAosJ6mu7fiyZeKqNKToQGDsM8hBADgUXoGAiqhSvp5QAnQKGIgUhwFUYLCVDFCrKUE1lBavAViFIDlTImbKC5Gm2hB0SlBCBMQiB0UjIQA7"));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public ImageView image;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = itemView.findViewById(R.id.album_item_name);
        }
    }
    private List<ItemAlbumes> mItems;

    public ItemsAlbumesAdapter(List<ItemAlbumes> items) {
        mItems = items;
    }
}
