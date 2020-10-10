
package com.baccaventuri.flicking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class RecyclerViewFragmentAlbumes extends Fragment {

    private static final String TAG = "RecyclerViewFragment";

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;


    protected ArrayList<ItemAlbumes> dataAlbums;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        dataAlbums = ItemAlbumes.createItemsList(500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recycler_view_albumes, container, false);
        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.albView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();

        ItemsAlbumesAdapter adapter = new ItemsAlbumesAdapter(dataAlbums);

        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(adapter);
        // END_INCLUDE(initializeRecyclerView)

        return rootView;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }
}
