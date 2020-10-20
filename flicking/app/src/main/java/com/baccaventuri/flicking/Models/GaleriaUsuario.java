package com.baccaventuri.flicking.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GaleriaUsuario {
    @SerializedName("photosets")
    @Expose
    private Photosets photosets;
    @SerializedName("stat")
    @Expose
    private String stat;

    /**
     * No args constructor for use in serialization
     *
     */
    public GaleriaUsuario() {
    }

    /**
     *
     * @param photosets
     * @param stat
     */
    public GaleriaUsuario(Photosets photosets, String stat) {
        super();
        this.photosets = photosets;
        this.stat = stat;
    }

    public Photosets getPhotosets() {
        return photosets;
    }

    public void setPhotosets(Photosets photosets) {
        this.photosets = photosets;
    }

    public GaleriaUsuario withPhotosets(Photosets photosets) {
        this.photosets = photosets;
        return this;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public GaleriaUsuario withStat(String stat) {
        this.stat = stat;
        return this;
    }
}
