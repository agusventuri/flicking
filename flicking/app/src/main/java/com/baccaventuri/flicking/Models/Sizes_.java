package com.baccaventuri.flicking.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sizes_ {

    @SerializedName("canblog")
    @Expose
    private String canblog;
    @SerializedName("canprint")
    @Expose
    private String canprint;
    @SerializedName("candownload")
    @Expose
    private String candownload;
    @SerializedName("size")
    @Expose
    private List<Size> size = null;

    public String getCanblog() {
        return canblog;
    }

    public void setCanblog(String canblog) {
        this.canblog = canblog;
    }

    public String getCanprint() {
        return canprint;
    }

    public void setCanprint(String canprint) {
        this.canprint = canprint;
    }

    public String getCandownload() {
        return candownload;
    }

    public void setCandownload(String candownload) {
        this.candownload = candownload;
    }

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

}