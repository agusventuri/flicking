package com.baccaventuri.flicking.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photoset_ {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("ownername")
    @Expose
    private String ownername;
    @SerializedName("photo")
    @Expose
    private List<Photo> photo = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("total")
    @Expose
    private String total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}