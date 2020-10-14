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
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private String farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isprimary")
    @Expose
    private String isprimary;
    @SerializedName("ispublic")
    @Expose
    private String ispublic;
    @SerializedName("isfriend")
    @Expose
    private String isfriend;
    @SerializedName("isfamily")
    @Expose
    private String isfamily;

    private Gson gson;
    private List<Size> size;
    private Bitmap bitmap;
    private AlbumsAdapter mAdapter;
    private int position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
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

    public String getIspublic() {
        return ispublic;
    }

    public void setIspublic(String ispublic) {
        this.ispublic = ispublic;
    }

    public String getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(String isfriend) {
        this.isfriend = isfriend;
    }

    public String getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(String isfamily) {
        this.isfamily = isfamily;
    }

    public void fetchBitmap(AlbumsAdapter mAdapter, int position) {
        this.mAdapter = mAdapter;
        this.position = position;

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
                        mAdapter.notifyItemChanged(position);
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