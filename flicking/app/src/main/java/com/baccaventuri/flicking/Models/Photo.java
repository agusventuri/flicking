package com.baccaventuri.flicking.Models;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.baccaventuri.flicking.AlbumsAdapter;
import com.baccaventuri.flicking.Flicking;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Photo {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isprimary")
    @Expose
    private String isprimary;

    private Gson gson;
    private List<Size> size;
    private Bitmap bitmap;
    private AlbumsAdapter mAdapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsprimary() {
        return isprimary;
    }

    public void setIsprimary(String isprimary) {
        this.isprimary = isprimary;
    }

    public void fetchBitmap(AlbumsAdapter mAdapter) {
        this.mAdapter = mAdapter;

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        StringBuilder url = new StringBuilder();
        url.append("https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=");
        url.append("6e69c76253dbd558d5bcb0e797676a69");
        url.append("&photo_id=");
        url.append(id);
        url.append("&format=json&nojsoncallback=1");

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), onGetSizesLoaded, onGetSizesError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetSizesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Sizes sizes = gson.fromJson(response, Sizes.class);
            size = sizes.getSizes().getSize();

            String url = size.get(6).getSource();

            ImageLoader imageLoader = Flicking.getImageLoader();
            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    assert true;
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        bitmap = response.getBitmap();
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };

    private final Response.ErrorListener onGetSizesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            assert true;
        }
    };

    public Bitmap getBitmap(){
        return bitmap;
    }

}