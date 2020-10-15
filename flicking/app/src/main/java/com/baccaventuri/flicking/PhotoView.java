package com.baccaventuri.flicking;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baccaventuri.flicking.Models.Photo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoView} factory method to
 * create an instance of this fragment.
 */
public class PhotoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "description";

    // TODO: Rename and change types of parameters
    private String name;
    private Drawable image;
    private Photo photo;

    public PhotoView(Photo photo) {
        // Required empty public constructor
        this.photo = photo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
           // image = getArguments().getString(ARG_PARAM2);
        }
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
        //lleno toolbar
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_photo);
        toolbar.setTitle(photo.getTitle());
        //lleno datos foto
        //TextView description = (TextView) getActivity().findViewById(R.id.description);
        //description.setText();
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.photo);
//        imageView.setImageDrawable(photo.getBitmap());
        imageView.setImageBitmap(photo.getBitmap());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_view, container, false);
    }


}