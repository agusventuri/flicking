package com.baccaventuri.flicking.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.baccaventuri.flicking.Models.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Query("SELECT * from Photos")
    LiveData<List<Photo>> getAllPhotos();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photo photo);

    @Query("DELETE FROM Photos")
    void deleteAll();
}
