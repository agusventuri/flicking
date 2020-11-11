package com.baccaventuri.flicking;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baccaventuri.flicking.Data.DataProvider;
import com.baccaventuri.flicking.Models.Album;
import com.baccaventuri.flicking.Models.Photo;

import android.content.SharedPreferences;

import java.util.List;

public class AlbumView extends Fragment {

    private AlbumsAdapter mAdapter;

    private List<Photo> photos;
    private Album album;
    MainActivity activity;
    DataProvider dataProvider;

    SharedPreferences sharedpreferences;
    public static final String sortPref = "sortPref";
    public static final String SortPicsByNameKey = "SortPicsByName";
    public static final String SortPicsByNameAscKey = "SortPicsByNameAsc";
    public static final String SortPicsByDateAscKey = "SortPicsByDateAsc";

    public AlbumView(Album album, MainActivity activity) {
        // Required empty public constructor
        this.album = album;
        this.photos = album.getPhoto();
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!activity.isOnline()) {
            NotificationUtils mNotificationUtils = new NotificationUtils(activity);

            Notification.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification(getString(R.string.offline),
                            getString(R.string.offlineAlbum));

            mNotificationUtils.getManager().notify(101, nb.build());
        }
        cargarFotos();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_album);
        toolbar.setTitle(album.getTitle());

        sharedpreferences = getActivity().getSharedPreferences(sortPref,
                Context.MODE_PRIVATE);

        //obtengo que tipo de orden es
        //si es orden x nombre...
        boolean orderByName;
        boolean asc;
        if(sharedpreferences.getBoolean(SortPicsByNameKey,true)){
            orderByName =true;
            asc = sharedpreferences.getBoolean(SortPicsByNameAscKey, true);
            //si es orden x fecha...
        }else{
            orderByName =false;
            asc = sharedpreferences.getBoolean(SortPicsByDateAscKey, true);
        }

        dataProvider.setContext(getContext());
        dataProvider.loadPhotoset(mAdapter,album, orderByName, asc, toolbar, getActivity());

        RecyclerView albumRecyclerView = getActivity().findViewById(R.id.albumRecyclerView);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        albumRecyclerView.setLayoutManager(layoutManager);
        albumRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_view, container, false);
    }

    public void cargarFotos (){
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_album);

        //preferencias para ordenamiento
        sharedpreferences = getActivity().getSharedPreferences(sortPref,
                Context.MODE_PRIVATE);

        mAdapter = new AlbumsAdapter(getContext(), photos,(AlbumsAdapter.PhotoClickListener) this.getActivity());
    }

    public void setDataProvider(DataProvider dp) {
        this.dataProvider = dp;
    }
}