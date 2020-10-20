package com.baccaventuri.flicking.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gallery {

    private long page;
    private long pages;
    private long perpage;
    private long total;
    private List<Album> photoset = new ArrayList<Album>();

    /**
     * No args constructor for use in serialization
     *
     */
    public Gallery() {
    }

    /**
     *
     * @param perpage
     * @param total
     * @param pages
     * @param page
     * @param photoset
     */
    public Gallery(long page, long pages, long perpage, long total, List<Album> photoset) {
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

    public Gallery withPage(long page) {
        this.page = page;
        return this;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages;
    }

    public Gallery withPages(long pages) {
        this.pages = pages;
        return this;
    }

    public long getPerpage() {
        return perpage;
    }

    public void setPerpage(long perpage) {
        this.perpage = perpage;
    }

    public Gallery withPerpage(long perpage) {
        this.perpage = perpage;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Gallery withTotal(long total) {
        this.total = total;
        return this;
    }

    public List<Album> getPhotoset() {
        return photoset;
    }

    public void setPhotoset(List<Album> photoset) {
        this.photoset = photoset;
    }

    public Gallery withPhotoset(List<Album> photoset) {
        this.photoset = photoset;
        return this;
    }

}