package com.baccaventuri.flicking.Models;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "Galleries")
public class Gallery {

        @PrimaryKey
        @NonNull
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("gallery_id")
        @Expose
        private String galleryId;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("owner")
        @Expose
        private String owner;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("iconserver")
        @Expose
        private String iconserver;
        @SerializedName("iconfarm")
        @Expose
        private Long iconfarm;
        @SerializedName("primary_photo_id")
        @Expose
        private String primaryPhotoId;
        @SerializedName("date_create")
        @Expose
        private String dateCreate;
        @SerializedName("date_update")
        @Expose
        private String dateUpdate;
        @SerializedName("count_photos")
        @Expose
        private Long countPhotos;
        @SerializedName("count_videos")
        @Expose
        private Long countVideos;
        @SerializedName("count_total")
        @Expose
        private Long countTotal;
        @SerializedName("count_views")
        @Expose
        private Long countViews;
        @SerializedName("count_comments")
        @Expose
        private Long countComments;
        @SerializedName("title")
        @Expose
        private String title;
/*        @SerializedName("description")
        @Expose
        private Description description;
        @SerializedName("sort_group")
        @Expose
        private Object sortGroup;*/
        @SerializedName("primary_photo_server")
        @Expose
        private String primaryPhotoServer;
        @SerializedName("primary_photo_farm")
        @Expose
        private Long primaryPhotoFarm;
        @SerializedName("primary_photo_secret")
        @Expose
        private String primaryPhotoSecret;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
        return title;
    }

        public void setTitle(String title) {
        this.title = title;
    }

        public Gallery withId(String id) {
            this.id = id;
            return this;
        }

        public String getGalleryId() {
            return galleryId;
        }

        public void setGalleryId(String galleryId) {
            this.galleryId = galleryId;
        }

        public Gallery withGalleryId(String galleryId) {
            this.galleryId = galleryId;
            return this;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Gallery withUrl(String url) {
            this.url = url;
            return this;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public Gallery withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Gallery withUsername(String username) {
            this.username = username;
            return this;
        }

        public String getIconserver() {
            return iconserver;
        }

        public void setIconserver(String iconserver) {
            this.iconserver = iconserver;
        }

        public Gallery withIconserver(String iconserver) {
            this.iconserver = iconserver;
            return this;
        }

        public Long getIconfarm() {
            return iconfarm;
        }

        public void setIconfarm(Long iconfarm) {
            this.iconfarm = iconfarm;
        }

        public Gallery withIconfarm(Long iconfarm) {
            this.iconfarm = iconfarm;
            return this;
        }

        public String getPrimaryPhotoId() {
            return primaryPhotoId;
        }

        public void setPrimaryPhotoId(String primaryPhotoId) {
            this.primaryPhotoId = primaryPhotoId;
        }

        public Gallery withPrimaryPhotoId(String primaryPhotoId) {
            this.primaryPhotoId = primaryPhotoId;
            return this;
        }

        public String getDateCreate() {
            return dateCreate;
        }

        public void setDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
        }

        public Gallery withDateCreate(String dateCreate) {
            this.dateCreate = dateCreate;
            return this;
        }

        public String getDateUpdate() {
            return dateUpdate;
        }

        public void setDateUpdate(String dateUpdate) {
            this.dateUpdate = dateUpdate;
        }

        public Gallery withDateUpdate(String dateUpdate) {
            this.dateUpdate = dateUpdate;
            return this;
        }

        public Long getCountPhotos() {
            return countPhotos;
        }

        public void setCountPhotos(Long countPhotos) {
            this.countPhotos = countPhotos;
        }

        public Gallery withCountPhotos(Long countPhotos) {
            this.countPhotos = countPhotos;
            return this;
        }

        public Long getCountVideos() {
            return countVideos;
        }

        public void setCountVideos(Long countVideos) {
            this.countVideos = countVideos;
        }

        public Gallery withCountVideos(Long countVideos) {
            this.countVideos = countVideos;
            return this;
        }

        public Long getCountTotal() {
            return countTotal;
        }

        public void setCountTotal(Long countTotal) {
            this.countTotal = countTotal;
        }

        public Gallery withCountTotal(Long countTotal) {
            this.countTotal = countTotal;
            return this;
        }

        public Long getCountViews() {
            return countViews;
        }

        public void setCountViews(Long countViews) {
            this.countViews = countViews;
        }

        public Gallery withCountViews(Long countViews) {
            this.countViews = countViews;
            return this;
        }

        public Long getCountComments() {
            return countComments;
        }

        public void setCountComments(Long countComments) {
            this.countComments = countComments;
        }

        public Gallery withCountComments(Long countComments) {
            this.countComments = countComments;
            return this;
        }

        /*public Object getSortGroup() {
            return sortGroup;
        }

        public void setSortGroup(Object sortGroup) {
            this.sortGroup = sortGroup;
        }

        public Gallery withSortGroup(Object sortGroup) {
            this.sortGroup = sortGroup;
            return this;
        }*/

        public String getPrimaryPhotoServer() {
            return primaryPhotoServer;
        }

        public void setPrimaryPhotoServer(String primaryPhotoServer) {
            this.primaryPhotoServer = primaryPhotoServer;
        }

        public Gallery withPrimaryPhotoServer(String primaryPhotoServer) {
            this.primaryPhotoServer = primaryPhotoServer;
            return this;
        }

        public Long getPrimaryPhotoFarm() {
            return primaryPhotoFarm;
        }

        public void setPrimaryPhotoFarm(Long primaryPhotoFarm) {
            this.primaryPhotoFarm = primaryPhotoFarm;
        }

        public Gallery withPrimaryPhotoFarm(Long primaryPhotoFarm) {
            this.primaryPhotoFarm = primaryPhotoFarm;
            return this;
        }

        public String getPrimaryPhotoSecret() {
            return primaryPhotoSecret;
        }

        public void setPrimaryPhotoSecret(String primaryPhotoSecret) {
            this.primaryPhotoSecret = primaryPhotoSecret;
        }

        public Gallery withPrimaryPhotoSecret(String primaryPhotoSecret) {
            this.primaryPhotoSecret = primaryPhotoSecret;
            return this;
        }

    }
