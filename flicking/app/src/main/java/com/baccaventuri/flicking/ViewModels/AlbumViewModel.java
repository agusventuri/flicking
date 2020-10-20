package com.baccaventuri.flicking.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.baccaventuri.flicking.Data.AlbumRepository;
import com.baccaventuri.flicking.Models.Photoset_;
import com.baccaventuri.flicking.Models.Photoset;

import java.util.List;

public class AlbumViewModel extends AndroidViewModel {

    private AlbumRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private LiveData<List<Photoset_>> mAllPhotoSet;

    public AlbumViewModel(Application application) {
        super(application);
        mRepository = new AlbumRepository(application);
        mAllPhotoSet = mRepository.getAllPhotoSet();
    }

    public LiveData<List<Photoset_>> getAllAlbums() {
        return mAllPhotoSet;
    }

    public void insert(Photoset album) {
        mRepository.insert(album);
    }
}
