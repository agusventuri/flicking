package com.baccaventuri.flicking.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("author_is_deleted")
    @Expose
    private Integer authorIsDeleted;
    @SerializedName("authorname")
    @Expose
    private String authorname;
    @SerializedName("iconserver")
    @Expose
    private String iconserver;
    @SerializedName("iconfarm")
    @Expose
    private Integer iconfarm;
    @SerializedName("datecreate")
    @Expose
    private String datecreate;
    @SerializedName("permalink")
    @Expose
    private String permalink;
    @SerializedName("path_alias")
    @Expose
    private String pathAlias;
    @SerializedName("realname")
    @Expose
    private String realname;
    @SerializedName("_content")
    @Expose
    private String content;

    /**
     * No args constructor for use in serialization
     *
     */
    public Comment() {
    }

    /**
     *
     * @param authorIsDeleted
     * @param iconfarm
     * @param iconserver
     * @param author
     * @param authorname
     * @param id
     * @param datecreate
     * @param permalink
     * @param content
     * @param pathAlias
     * @param realname
     */
    public Comment(String id, String author, Integer authorIsDeleted, String authorname, String iconserver, Integer iconfarm, String datecreate, String permalink, String pathAlias, String realname, String content) {
        super();
        this.id = id;
        this.author = author;
        this.authorIsDeleted = authorIsDeleted;
        this.authorname = authorname;
        this.iconserver = iconserver;
        this.iconfarm = iconfarm;
        this.datecreate = datecreate;
        this.permalink = permalink;
        this.pathAlias = pathAlias;
        this.realname = realname;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Comment withId(String id) {
        this.id = id;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Comment withAuthor(String author) {
        this.author = author;
        return this;
    }

    public Integer getAuthorIsDeleted() {
        return authorIsDeleted;
    }

    public void setAuthorIsDeleted(Integer authorIsDeleted) {
        this.authorIsDeleted = authorIsDeleted;
    }

    public Comment withAuthorIsDeleted(Integer authorIsDeleted) {
        this.authorIsDeleted = authorIsDeleted;
        return this;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public Comment withAuthorname(String authorname) {
        this.authorname = authorname;
        return this;
    }

    public String getIconserver() {
        return iconserver;
    }

    public void setIconserver(String iconserver) {
        this.iconserver = iconserver;
    }

    public Comment withIconserver(String iconserver) {
        this.iconserver = iconserver;
        return this;
    }

    public Integer getIconfarm() {
        return iconfarm;
    }

    public void setIconfarm(Integer iconfarm) {
        this.iconfarm = iconfarm;
    }

    public Comment withIconfarm(Integer iconfarm) {
        this.iconfarm = iconfarm;
        return this;
    }

    public String getDatecreate() {
        return datecreate;
    }

    public void setDatecreate(String datecreate) {
        this.datecreate = datecreate;
    }

    public Comment withDatecreate(String datecreate) {
        this.datecreate = datecreate;
        return this;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public Comment withPermalink(String permalink) {
        this.permalink = permalink;
        return this;
    }

    public String getPathAlias() {
        return pathAlias;
    }

    public void setPathAlias(String pathAlias) {
        this.pathAlias = pathAlias;
    }

    public Comment withPathAlias(String pathAlias) {
        this.pathAlias = pathAlias;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Comment withRealname(String realname) {
        this.realname = realname;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment withContent(String content) {
        this.content = content;
        return this;
    }
}
