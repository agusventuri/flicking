package com.baccaventuri.flicking;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.baccaventuri.flicking.Data.DataProvider;
import com.baccaventuri.flicking.Models.Gallery;
import com.baccaventuri.flicking.Models.Album;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GalleryView} factory method to
 * create an instance of this fragment.
 */
public class GalleryView extends Fragment {

    private RecyclerView albumRecyclerView;
    private GalleryAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    MainActivity activity;
    DataProvider dataProvider;

    public GalleryView(MainActivity activity) {
        // Required empty public constructor
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!activity.isOnline()) {
            NotificationUtils mNotificationUtils = new NotificationUtils(activity);

            Notification.Builder nb = mNotificationUtils.
                    getAndroidChannelNotification("No se ha podido establecer conexión a internet",
                            "La galería cargada podría no estar actualizada");

            mNotificationUtils.getManager().notify(101, nb.build());
        }

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);


        mAdapter = new GalleryAdapter(getContext(), null, activity);
        dataProvider.loadGalleriaUsuario(mAdapter, toolbar, getActivity());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_album);
        toolbar.setTitle("Albumes de josue");

        albumRecyclerView = getActivity().findViewById(R.id.galeriaRecyclerView);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        albumRecyclerView.setLayoutManager(layoutManager);
        albumRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_galeria_view, container, false);
    }

    public boolean onBackPressed() {
        return true;
    }

    public void setDataProvider(DataProvider dp) {
        this.dataProvider = dp;
    }
}