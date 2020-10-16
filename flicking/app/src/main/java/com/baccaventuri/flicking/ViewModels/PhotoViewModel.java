package com.baccaventuri.flicking.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.baccaventuri.flicking.Data.PhotoRepository;
import com.baccaventuri.flicking.Models.Photo;

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
        mAllPhotos = mRepository.getAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return mAllPhotos;
    }

    public void insert(Photo photo) {
        mRepository.insert(photo);
    }
}
