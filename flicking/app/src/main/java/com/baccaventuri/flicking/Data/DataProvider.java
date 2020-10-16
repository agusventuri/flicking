package com.baccaventuri.flicking.Data;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baccaventuri.flicking.AlbumsAdapter;
import com.baccaventuri.flicking.Flicking;
import com.baccaventuri.flicking.Models.Photo;
import com.baccaventuri.flicking.Models.Photoset;
import com.baccaventuri.flicking.ViewModels.PhotoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class DataProvider {

    private Gson gson;
    private AlbumsAdapter mAlbumsAdapter;
    private Toolbar albumToolbar;
    private PhotoViewModel mPhotoViewModel;

    public void loadPhotoset (AlbumsAdapter mAlbumsAdapter, Toolbar albumToolbar, FragmentActivity activity) {
        this.mAlbumsAdapter = mAlbumsAdapter;
        this.albumToolbar = albumToolbar;

        mPhotoViewModel = new ViewModelProvider(activity).get(PhotoViewModel.class);

        mPhotoViewModel.getAllPhotos().observe(activity, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable final List<Photo> photos) {
                // Update the cached copy of the words in the adapter.
                mAlbumsAdapter.updateDataset(photos);
            }
        });

        List<Photo> photos = mPhotoViewModel.getAllPhotos().getValue();

        if (photos != null) {
            for (Photo photo:photos) {
                if (photo.getBitmap() == null) {
                    photo.fetchBitmap(mAlbumsAdapter);
                }
            }
            mAlbumsAdapter.updateDataset(photos);
            mAlbumsAdapter.notifyDataSetChanged();
        } else {
            fetchPhotoset();
        }

    }

    private void fetchPhotoset() {
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
                mPhotoViewModel.insert(photo);

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
