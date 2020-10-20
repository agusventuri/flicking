package com.baccaventuri.flicking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.baccaventuri.flicking.Models.Album;

import java.util.List;

@Dao
public interface AlbumDao {

    //@Transaction
    @Query("SELECT * from Albums")
    LiveData<List<Album>> getAlbums();
    //public List<AlbumPhotos> getAlbums();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Album album);

    @Query("DELETE FROM Albums")
    void deleteAll();
}
