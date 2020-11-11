package com.baccaventuri.flicking.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.graphics.Bitmap;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;
import androidx.annotation.NonNull;


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
    private Bitmap bitmap;

    @SerializedName("bitmapUri")
    @Expose
    private String bitmapUri;

    @SerializedName("taken")
    @Expose
    private Date taken;

    @SerializedName("secret")
    @Expose
    private String secret;

    @SerializedName("server")
    @Expose
    private String server;

    @SerializedName("photoset")
    @Expose
    private String photoset;

    @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "id_photoset")

    @NonNull
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

    public String getTakenString() {
        if (taken == null) {
            return "No hay fecha";
        }

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(taken);
    }

    public Date getTaken() {
        return taken;
    }

    public void setTaken(Date taken) { this.taken = taken; }

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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

}