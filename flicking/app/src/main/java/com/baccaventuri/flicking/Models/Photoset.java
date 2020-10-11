package com.baccaventuri.flicking.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photoset {

    @SerializedName("photoset")
    @Expose
    private Photoset_ photoset;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Photoset_ getPhotoset() {
        return photoset;
    }

    public void setPhotoset(Photoset_ photoset) {
        this.photoset = photoset;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
