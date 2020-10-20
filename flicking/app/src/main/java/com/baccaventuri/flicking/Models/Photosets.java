package com.baccaventuri.flicking.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photosets {

    @SerializedName("page")
    @Expose
    private long page;
    @SerializedName("pages")
    @Expose
    private long pages;
    @SerializedName("perpage")
    @Expose
    private long perpage;
    @SerializedName("total")
    @Expose
    private long total;
    @SerializedName("photoset")
    @Expose
    private List<Photoset> photoset = new ArrayList<Photoset>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Photosets() {
    }

    /**
     *
     * @param perpage
     * @param total
     * @param pages
     * @param page
     * @param photoset
     */
    public Photosets(long page, long pages, long perpage, long total, List<Photoset> photoset) {
        super();
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.photoset = photoset;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public Photosets withPage(long page) {
        this.page = page;
        return this;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public Photosets withPages(long pages) {
        this.pages = pages;
        return this;
    }

    public long getPerpage() {
        return perpage;
    }

    public void setPerpage(long perpage) {
        this.perpage = perpage;
    }

    public Photosets withPerpage(long perpage) {
        this.perpage = perpage;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Photosets withTotal(long total) {
        this.total = total;
        return this;
    }

    public List<Photoset> getPhotoset() {
        return photoset;
    }

    public void setPhotoset(List<Photoset> photoset) {
        this.photoset = photoset;
    }

    public Photosets withPhotoset(List<Photoset> photoset) {
        this.photoset = photoset;
        return this;
    }

}