package com.baccaventuri.flicking.ViewModels;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.baccaventuri.flicking.Data.PhotoRepository;
import com.baccaventuri.flicking.Models.Photo;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class PhotoViewModel extends AndroidViewModel {

    private PhotoRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Photo>> mAllPhotos;

    public PhotoViewModel(Application application) {
        super(application);
        mRepository = new PhotoRepository(application);
    }

    public LiveData<List<Photo>> getAllPhotos(String album, Boolean orderByName, Boolean asc) {
        mAllPhotos = mRepository.getAllPhotos(album,orderByName,asc);
        if (mAllPhotos.getValue() != null) {
            for (Photo photo : mAllPhotos.getValue()) {
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getApplication().getContentResolver(), Uri.parse(photo.getBitmapUri()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                photo.setBitmap(bitmap);
            }
        }
        return mAllPhotos;
    }

    public Boolean isEmpty () {
        try {
            return mAllPhotos.getValue().isEmpty();
        } catch (NullPointerException e) {
            Log.d("exception photos", e.getMessage() + "");
        }
        return true;
    }

    public void insert(Photo photo) {
        mRepository.insert(photo);
    }

    public void update(Date created, String id) {
        for (Photo photo : mAllPhotos.getValue()){
            if (photo.getId().equals(id.toString())) {
                photo.setTaken(created);
                mRepository.update(photo);
            }
        }
    }
}
