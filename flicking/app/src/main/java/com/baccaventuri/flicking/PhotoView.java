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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhotoView#newInstance} factory method to
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

    public PhotoView(String name, Drawable image) {
        // Required empty public constructor
        this.name = name;
        this.image = image;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param image Parameter 2.
     * @return A new instance of fragment PhotoView.
     */
    // TODO: Rename and change types and number of parameters
/*    public static PhotoView newInstance(String name, Drawable image) {
        PhotoView fragment = new PhotoView(name,image);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, image);
        fragment.setArguments(args);
        return fragment;
    }*/

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
        toolbar.setTitle(name);
        //lleno datos foto
        //TextView description = (TextView) getActivity().findViewById(R.id.description);
        //description.setText();
        ImageView photo = (ImageView) getActivity().findViewById(R.id.photo);
        photo.setImageDrawable(image);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_view, container, false);
    }


}