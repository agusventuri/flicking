package com.baccaventuri.flicking.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AlbumPhotos {
    @Embedded
    public Photoset_ album;
    @Relation(
            parentColumn = "id",
            entityColumn = "id"
    )
    public List<Photo> photos;
}