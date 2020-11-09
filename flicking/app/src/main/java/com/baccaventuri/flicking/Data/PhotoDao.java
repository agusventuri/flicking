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

    @Query("SELECT * from Photos WHERE photoset = :album  ORDER BY title ASC")
    LiveData<List<Photo>> getPhotosNameASC(String album);

    @Query("SELECT * from Photos WHERE photoset = :album  ORDER BY title DESC")
    LiveData<List<Photo>> getPhotosNameDESC(String album);

    @Query("SELECT * from Photos WHERE photoset = :album  ORDER BY taken ASC")
    LiveData<List<Photo>> getPhotosDateASC(String album);

    @Query("SELECT * from Photos WHERE photoset = :album  ORDER BY taken DESC")
    LiveData<List<Photo>> getPhotosDateDESC(String album);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Photo photo);

    @Query("DELETE FROM Photos")
    void deleteAll();
}
