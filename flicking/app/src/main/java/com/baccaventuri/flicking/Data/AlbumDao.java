package com.baccaventuri.flicking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.baccaventuri.flicking.Models.AlbumPhotos;
import com.baccaventuri.flicking.Models.Photoset;
import com.baccaventuri.flicking.Models.Photoset_;

import java.util.List;

@Dao
public interface AlbumDao {

    //@Transaction
    @Query("SELECT * from Albums")
    LiveData<List<Photoset_>> getAlbums();
    //public List<AlbumPhotos> getAlbums();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photoset_ album);

    @Query("DELETE FROM Albums")
    void deleteAll();
}
