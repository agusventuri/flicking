package com.baccaventuri.flicking.Models;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;
import java.util.List;
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

    @SerializedName("secret")
    @Expose
    private String secret;

    @SerializedName("server")
    @Expose
    private String server;

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

    public void setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }

    public Bitmap getBitmap(){ return bitmap; }

}