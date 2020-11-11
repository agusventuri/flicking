package com.baccaventuri.flicking.Models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import androidx.annotation.NonNull;


import java.util.List;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.baccaventuri.flicking.Flicking;
import com.baccaventuri.flicking.GalleryAdapter;
import com.baccaventuri.flicking.ViewModels.AlbumViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Albums")
public class Album {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("primary")
    @Expose
    private String primary;

    @SerializedName("uriPrimary")
    @Expose
    private String uriPrimary;

    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("ownername")
    @Expose
    private String ownername;

    @SerializedName("photo")
    @Ignore
    private List<Photo> photo;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("total")
    @Expose
    private String total;
    @Ignore
    private Bitmap bitmap;

    @Ignore
    private List<Size> size;

    @SerializedName("bitmapUri")
//    @Expose
//    private String bitmapUri;

    @Ignore
    private GalleryAdapter mAdapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUriPrimary() {
        return uriPrimary;
    }

    public void setUriPrimary(String uriPrimary) {
        this.uriPrimary = uriPrimary;
    }
    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Ignore
    public AlbumViewModel albumViewModel;

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    public Bitmap getBitmap(){ return bitmap; }

    public void fetchBitmap(GalleryAdapter mAdapter) {
        this.mAdapter = mAdapter;

        StringBuilder url = new StringBuilder();
        url.append("https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=");
        url.append("6e69c76253dbd558d5bcb0e797676a69");
        url.append("&photo_id=");
        url.append(primary);
        url.append("&format=json&nojsoncallback=1");
        String a;
        a= "https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=6e69c76253dbd558d5bcb0e797676a69&photo_id=6895430587&format=json&nojsoncallback=1";

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), onGetSizesLoaded, onGetSizesError);
        Flicking.getSharedQueue().add(request);
    }

    @Ignore
    private final Response.Listener<String> onGetSizesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            Sizes sizes = gson.fromJson(response, Sizes.class);
            size = sizes.getSizes().getSize();

            String url = size.get(6).getSource();
            Log.d("AYUDA",url);

            ImageLoader imageLoader = Flicking.getImageLoader();
            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   assert true;
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    Log.d("OUTSIDE",response.toString());
                    if (response.getBitmap() != null) {
                        bitmap = response.getBitmap();
                        setUriPrimary(response.getRequestUrl());
                        setBitmap(bitmap);
                        Log.d("INSIDE",bitmap.toString());
                        mAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    };

    @Ignore
    private final Response.ErrorListener onGetSizesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            assert true;
        }
    };

}