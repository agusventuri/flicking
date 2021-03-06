package com.baccaventuri.flicking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.baccaventuri.flicking.Data.DataProvider;
import com.baccaventuri.flicking.Models.Photo;
import com.baccaventuri.flicking.Models.Album;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AlbumsAdapter.PhotoClickListener, GalleryAdapter.AlbumClickListener {
    SharedPreferences sharedpreferences;
    public static final String sortPref = "sortPref";
    public static final String SortPicsByNameKey = "SortPicsByName";
    public static final String SortPicsByNameAscKey = "SortPicsByNameAsc";
    public static final String SortPicsByDateAscKey = "SortPicsByDateAsc";
    public static final String LastStartup = "LastStartup";
    public static final String RefreshAll = "RefreshAll";
    public static final String RefreshOne = "null";
    public DataProvider dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        sharedpreferences = getSharedPreferences(sortPref,
                Context.MODE_PRIVATE);

        dp = new DataProvider();
        dp.setContext(getApplicationContext());
        dp.setSharedPreferences(sharedpreferences);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        if (!sharedpreferences.contains(SortPicsByNameKey) ||
                !sharedpreferences.contains(SortPicsByNameAscKey) ||
                !sharedpreferences.contains(SortPicsByDateAscKey)){
            editor.putBoolean(SortPicsByNameKey, true);
            editor.putBoolean(SortPicsByNameAscKey, true);
            editor.putBoolean(SortPicsByDateAscKey, false);
        }

        editor.putBoolean(RefreshAll, false);

        if (sharedpreferences.contains(LastStartup)) {
            Date now = Calendar.getInstance().getTime();
            String lastStartup = sharedpreferences.getString(LastStartup, now.toString());
            Date lastStartupDate = Calendar.getInstance().getTime();
            try {
                lastStartupDate = new SimpleDateFormat().parse(lastStartup);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long diff = now.getTime() - lastStartupDate.getTime();
            if (diff >= 3600000) {
                editor.putBoolean(RefreshAll, true);
            }
        } else {
            editor.putBoolean(RefreshAll, true);
        }

        editor.putString(LastStartup, Calendar.getInstance().getTime().toString());
        editor.apply();

        pasarAGalleryFrag();
    }


    public void pasarAPhotoFrag (Photo photo) {

        // Create fragment and give it an argument for the selected photo
        PhotoView newFragment = new PhotoView(photo);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment,"PHOTO");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public void pasarAalbumFrag (Album album) {
        // Create fragment and give it an argument for the selected article
        AlbumView newFragment = new AlbumView(album,this);
        newFragment.setDataProvider(dp);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment,"ALBUM");
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public void pasarAGalleryFrag () {

        // Create fragment and give it an argument for the selected article
        GalleryView newFragment = new GalleryView(this);
        newFragment.setDataProvider(dp);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public void changeSortingMethod (MenuItem item) {
        boolean sortByDate = true;
        if (item.getTitle().equals(getString(R.string.filtro_nombre))) {
            sortByDate = false;
        }

        sharedpreferences = getSharedPreferences(sortPref,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        String sortingByText;

        // el usuario me pide que ahora filtre por fecha
        if (sortByDate) {
            // si actualmente el filtro por nombre esta activo, lo cambio a filtro por fecha
            // sino, cambio el orden de filtrado de fecha
            if (sharedpreferences.getBoolean(SortPicsByNameKey, false)){
                editor.putBoolean(SortPicsByNameKey, false);
                editor.putBoolean(SortPicsByDateAscKey, true);
                sortingByText = getString(R.string.sortedByDateAsc);
            } else {
                editor.putBoolean(SortPicsByDateAscKey,
                        !sharedpreferences.getBoolean(SortPicsByDateAscKey, false));
                sortingByText = !sharedpreferences.getBoolean(SortPicsByDateAscKey, false)
                        ? getString(R.string.sortedByDateAsc)
                        : getString(R.string.sortedByDateDesc);
            }
        } else {
            // aca me esta pidiendo que filtre por nombre
            // si actualmente el filtro por nombre esta activo, cambio el orden de filtrado
            // sino, cambio el filtro a filtrado por nombre
            if (sharedpreferences.getBoolean(SortPicsByNameKey, false)) {
                editor.putBoolean(SortPicsByNameAscKey,
                        !sharedpreferences.getBoolean(SortPicsByNameAscKey, false));
                sortingByText = !sharedpreferences.getBoolean(SortPicsByNameAscKey, false)
                        ? getString(R.string.sortedByNameAsc)
                        : getString(R.string.sortedByNameDesc);
            } else {
                editor.putBoolean(SortPicsByNameKey, true);
                editor.putBoolean(SortPicsByNameAscKey, true);
                sortingByText = getString(R.string.sortedByNameAsc);
            }
        }

        Toast.makeText(this, sortingByText, Toast.LENGTH_SHORT).show();

        // guardo los nuevos ajustes
        editor.apply();
        //actualizo fragment album
        AlbumView frag = (AlbumView) getSupportFragmentManager().findFragmentByTag("ALBUM");
        if (frag != null){
            frag.setDataProvider(dp);
            frag.onResume();
        }
    }

    @Override
    public void onPhotoClick(Photo photo) {
        pasarAPhotoFrag(photo);
    }

    @Override
    public void onAlbumClick(Album album)  {
        pasarAalbumFrag(album);
    }

    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for(Fragment f : fragmentList) {
            if(f instanceof GalleryView) {
                handled = ((GalleryView)f).onBackPressed();

                if(handled) {
                    finish();
                    break;
                }
            }
        }

        if(!handled) {
            super.onBackPressed();
        }
    }

    public void sharePhoto(MenuItem item) {
        PhotoView frag = (PhotoView) getSupportFragmentManager().findFragmentByTag("PHOTO");
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");

        Uri imageUri = Uri.parse("content://" + "com.tdam.contentprovider.PhotoProvider" + File.separator + "Photos" + File.separator + frag.photo.getBitmapUri());
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(shareIntent);
    }

    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected() && netInfo.isAvailable();
    }
}