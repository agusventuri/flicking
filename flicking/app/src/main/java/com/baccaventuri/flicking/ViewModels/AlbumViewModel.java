package com.baccaventuri.flicking.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.baccaventuri.flicking.Data.AlbumRepository;
import com.baccaventuri.flicking.Models.Album;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {

    private AlbumRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Album>> mAllPhotoSet;

    public AlbumViewModel(Application application) {
        super(application);
        mRepository = new AlbumRepository(application);
        mAllPhotoSet = mRepository.getAllPhotoSet();
    }

    public LiveData<List<Album>> getAllAlbums() {
        return mAllPhotoSet;
    }

    public Boolean isEmpty () {
        try {
            return mAllPhotoSet.getValue().isEmpty();
        } catch (NullPointerException e) {
            Log.d("exception photoset", e.getMessage() + "");
        }
        return true;
    }

    public void insert(Album album) {
        mRepository.insert(album);
    }

    public void update(Album album) {
        mRepository.update(album);
    }
}
