package com.baccaventuri.flicking;

import android.annotation.SuppressLint;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView albumRecyclerView;
    private GalleriesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Album> albums;
    private Gallery gallery;
    MainActivity activity;

    public GalleryView(MainActivity activity) {
        // Required empty public constructor
        this.activity = activity;
    }

    // TODO: Rename and change types and number of parameters
   /* public static AlbumView newInstance(String param1, String param2) {
        AlbumView fragment = new AlbumView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);


        mAdapter = new GalleriesAdapter(getContext(), albums, activity);

        DataProvider dataProvider = new DataProvider();
        dataProvider.loadGalleriaUsuario(mAdapter, toolbar, getActivity());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
        super.onResume();
/*        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.menu_album);
        toolbar.setTitle("Albumes de josue");*/

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
}