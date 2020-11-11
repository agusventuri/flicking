package com.baccaventuri.flicking;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baccaventuri.flicking.Data.DataProvider;
import com.baccaventuri.flicking.Models.Photo;

public class PhotoView extends Fragment {

    private PhotoAdapter mAdapter;

    public Photo photo;

    public PhotoView(Photo photo) {
        this.photo = photo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PhotoAdapter(getContext(), null);

        DataProvider dataProvider = new DataProvider();
        dataProvider.loadPhoto(mAdapter,photo, getActivity());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();

        //lleno toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_photo);
        toolbar.setTitle(photo.getTitle());

        //lleno datos photo
        ImageView imageView = getActivity().findViewById(R.id.photo);
        imageView.setImageBitmap(photo.getBitmap());

        RecyclerView photoRecyclerView = getActivity().findViewById(R.id.commentRecyclerView);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        photoRecyclerView.setLayoutManager(layoutManager);
        photoRecyclerView.setAdapter(mAdapter);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("JOSUE","JOSUE");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_view, container, false);
    }





}