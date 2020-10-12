package com.baccaventuri.flicking.Models;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Sizes {

    @SerializedName("sizes")
    @Expose
    private Sizes_ sizes;
    @SerializedName("stat")
    @Expose
    private String stat;

    public Sizes_ getSizes() {
        return sizes;
    }

    public void setSizes(Sizes_ sizes) {
        this.sizes = sizes;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}