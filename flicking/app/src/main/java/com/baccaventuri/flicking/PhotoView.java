package com.baccaventuri.flicking;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
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
import com.baccaventuri.flicking.Models.Comment;
import com.baccaventuri.flicking.Models.Photo;

public class PhotoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "description";

    private RecyclerView photoRecyclerView;
    private PhotoAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    public Photo photo;

    public PhotoView(Photo photo) {
        // Required empty public constructor
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

        photoRecyclerView = getActivity().findViewById(R.id.commentRecyclerView);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
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