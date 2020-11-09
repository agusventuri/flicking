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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;

import static android.content.ContentValues.TAG;

@Entity(tableName = "Photos")
public class Photo {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isprimary")
    @Expose
    private String isprimary;

    @Ignore
    private List<Size> size;
    @Ignore
    private Bitmap bitmap;
    @SerializedName("bitmapUri")
    @Expose
    private String bitmapUri;

    @SerializedName("taken")
    @Expose
    private Date taken;

    @SerializedName("photoset")
    @Expose
    private String photoset;

    @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "id_photoset")



    @Ignore
    private AlbumsAdapter mAdapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotoset() {
        return photoset;
    }

    public void setPhotoset(String id) {
        this.photoset = id;
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

    public String getBitmapUri() {
        return bitmapUri;
    }

    public void setBitmapUri(String bitmapUri) {
        this.bitmapUri = bitmapUri;
    }

    public Date getTaken() { return taken; }

    public void setTaken(Date taken) { this.taken = taken; }

    public void fetchBitmap(AlbumsAdapter mAdapter) {
        this.mAdapter = mAdapter;

        StringBuilder url = new StringBuilder();
        url.append("https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=");
        url.append("6e69c76253dbd558d5bcb0e797676a69");
        url.append("&photo_id=");
        url.append(id);
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

    @Ignore
    private final Response.ErrorListener onGetSizesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            assert true;
        }
    };

    public Bitmap getBitmap() {
        return bitmap;
    }

    //carga de fechas
    public void fetchDates() {

        StringBuilder url = new StringBuilder();
        url.append("https://www.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=6e69c76253dbd558d5bcb0e797676a69&photo_id=");
        url.append(getId());
        url.append("&format=json&nojsoncallback=1");
        String a;
        a= "https://www.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=6e69c76253dbd558d5bcb0e797676a69&photo_id=6895430587&format=json&nojsoncallback=1";

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), onGetPhotoInfoLoaded, onGetPhotoInfoError);
        Flicking.getSharedQueue().add(request);
    }

    @Ignore
    private final Response.Listener<String> onGetPhotoInfoLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");

            JsonObject object = (JsonObject) new JsonParser().parse(response);
            JsonElement photo = object.get("photo");
            JsonObject photoobject = photo.getAsJsonObject();
            JsonElement dates = (JsonElement) photoobject.get("dates");
            Date created = new Date(dates.getAsJsonObject().get("taken").toString());
            String pep = "a";
            setTaken(created);
        }
    };

    @Ignore
    private final Response.ErrorListener onGetPhotoInfoError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            assert true;
        }
    };

}