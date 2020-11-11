package com.baccaventuri.flicking.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.baccaventuri.flicking.Models.Album;

import java.util.List;

public class AlbumRepository {

    private AlbumDao mAlbumDao;
    private LiveData<List<Album>> mAllAlbums;

    public AlbumRepository(Application application) {
        FlickingRoomDatabase db = FlickingRoomDatabase.getDatabase(application);
        mAlbumDao = db.albumDao();
        mAllAlbums = mAlbumDao.getAlbums();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Album>> getAllPhotoSet() {
        return mAllAlbums;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Album album) {
        FlickingRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAlbumDao.insert(album);
        });
    }

    public void update(Album album) {
        FlickingRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAlbumDao.update(album);
        });
    }
}
