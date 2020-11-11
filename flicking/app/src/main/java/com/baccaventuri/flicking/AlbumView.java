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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumView} factory method to
 * create an instance of this fragment.
 */
public class AlbumView extends Fragment {

    private RecyclerView albumRecyclerView;
    private AlbumsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Photo> photos;
    private Album album;
    MainActivity activity;
    DataProvider dataProvider;

    SharedPreferences sharedpreferences;
    public static final String sortPref = "sortPref";
    public static final String SortPicsByNameKey = "SortPicsByName";
    public static final String SortPicsByNameAscKey = "SortPicsByNameAsc";
    public static final String SortPicsByDateAscKey = "SortPicsByDateAsc";

    private Boolean orderByName;
    private Boolean asc;

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
                    getAndroidChannelNotification("No se ha podido establecer conexión a internet",
                            "El álbum cargado podría no estar actualizado");

            mNotificationUtils.getManager().notify(101, nb.build());
        }
        cargarFotos();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_album);
        toolbar.setTitle(album.getTitle());

        sharedpreferences = getActivity().getSharedPreferences(sortPref,
                Context.MODE_PRIVATE);

        //obtengo que tipo de orden es
        //si es orden x nombre...
        if(sharedpreferences.getBoolean(SortPicsByNameKey,true)){
            orderByName=true;
            if (sharedpreferences.getBoolean(SortPicsByNameAscKey,true)){
                asc=true;
            }else{
                asc=false;
            }
            //si es orden x fecha...
        }else{
            orderByName=false;
            if (sharedpreferences.getBoolean(SortPicsByDateAscKey,true)){
                asc=true;
            }else{
                asc=false;
            }
        }

        dataProvider.setContext(getContext());
        dataProvider.loadPhotoset(mAdapter,album,orderByName,asc, toolbar, getActivity());

        albumRecyclerView = getActivity().findViewById(R.id.albumRecyclerView);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
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