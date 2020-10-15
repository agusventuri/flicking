package com.baccaventuri.flicking.Data;

import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baccaventuri.flicking.AlbumsAdapter;
import com.baccaventuri.flicking.Flicking;
import com.baccaventuri.flicking.Models.Photo;
import com.baccaventuri.flicking.Models.Photoset;
import com.baccaventuri.flicking.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class DataProvider {

    private Gson gson;
    private AlbumsAdapter mAlbumsAdapter;
    private Toolbar albumToolbar;

    public void loadPhotoset (AlbumsAdapter mAlbumsAdapter, Toolbar albumToolbar) {
        this.mAlbumsAdapter = mAlbumsAdapter;
        this.albumToolbar = albumToolbar;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=6e69c76253dbd558d5bcb0e797676a69&photoset_id=72157628042948461&user_id=36587311%40N08&media=photos&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetPhotosLoaded, onGetPhotosError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetPhotosLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Photoset photoset = gson.fromJson(response, Photoset.class);
            List<Photo> photos = photoset.getPhotoset().getPhoto();
            mAlbumsAdapter.updateDataset(photos);

            for (Photo photo:photos) {
                if (photo.getBitmap() == null) {
                    photo.fetchBitmap(mAlbumsAdapter);
                }
            }

            albumToolbar.setTitle(photoset.getPhotoset().getTitle());
        }
    };

    private final Response.ErrorListener onGetPhotosError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
