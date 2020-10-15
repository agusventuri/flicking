package com.baccaventuri.flicking.Data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.baccaventuri.flicking.Models.Photo;

import java.util.List;

public class PhotoRepository {
    private PhotoDao mPhotoDao;
    private LiveData<List<Photo>> mAllPhotos;

    public PhotoRepository(Application application) {
        FlickingRoomDatabase db = FlickingRoomDatabase.getDatabase(application);
        mPhotoDao = db.photoDao();
        mAllPhotos = mPhotoDao.getAllPhotos();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Photo>> getAllPhotos() {
        return mAllPhotos;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Photo photo) {
        FlickingRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPhotoDao.insert(photo);
        });
    }
}
