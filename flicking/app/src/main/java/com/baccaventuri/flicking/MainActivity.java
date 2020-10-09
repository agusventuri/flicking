package com.baccaventuri.flicking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.baccaventuri.flicking.ui.main.AlbumView;
import com.baccaventuri.flicking.ui.main.PhotoView;
import com.baccaventuri.flicking.ui.main.SetsView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static androidx.core.content.FileProvider.getUriForFile;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String sortPref = "sortPref";
    public static final String SortPicsByNameKey = "SortPicsByName";
    public static final String SortPicsByNameAscKey = "SortPicsByNameAsc";
    public static final String SortPicsByDateAscKey = "SortPicsByDateAsc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Álbumes de Josue");
        setSupportActionBar(myToolbar);

        sharedpreferences = getSharedPreferences(sortPref,
                Context.MODE_PRIVATE);

        if (!sharedpreferences.contains(SortPicsByNameKey) ||
                !sharedpreferences.contains(SortPicsByNameAscKey) ||
                !sharedpreferences.contains(SortPicsByDateAscKey)){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(SortPicsByNameKey, true);
            editor.putBoolean(SortPicsByNameAscKey, true);
            editor.putBoolean(SortPicsByDateAscKey, false);
            editor.apply();
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SetsView.newInstance())
                    .commitNow();
        }
    }

    public void pasarAPhotoFrag (View view) {
        Context context = getApplicationContext();
        CharSequence text = "Vamo vieja!";
        int duration = Toast.LENGTH_SHORT;

        // Create fragment and give it an argument for the selected article
        PhotoView newFragment = new PhotoView();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    public void pasarAalbumFrag (View view) {
        Context context = getApplicationContext();

        // Create fragment and give it an argument for the selected article
        AlbumView newFragment = new AlbumView();
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

    }

    public void sharePhoto(MenuItem item) {
        Uri imageUri = Uri.parse("android.resource://com.baccaventuri.flicking/drawable/montania");

        File newFile = new File("android.resource://com.baccaventuri.flicking/drawable", "montania.png");
        Uri contentUri = getUriForFile(getApplicationContext(), "com.mydomain.fileprovider", newFile);
        //Uri imageUri = Uri.parse("https://i.pinimg.com/originals/91/c3/89/91c3894f5d1c0585cde7b66750a32062.png");
//        ImageView imageView = (ImageView) findViewById(R.id.photo);
//        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
//        shareIntent.setType("image/*");
//
//        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        startActivity(Intent.createChooser(shareIntent, "Share image using"));
//        Toast.makeText(this, imageView.getDrawable().toString(), Toast.LENGTH_SHORT).show();

//        Bitmap b =BitmapFactory.decodeResource(getResources(),R.drawable.montania);
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/*");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        b.compress(Bitmap.CompressFormat.PNG, 100, bytes);
//        //String path = MediaStore.Images.Media. insert(getContentResolver(), b, "Title", null);
//        //Uri imageUri =  Uri.parse(path);
//
//        share.putExtra(Intent.EXTRA_STREAM, imageUri);
//        startActivity(Intent.createChooser(share, "Select"));

        Intent shareIntent = new Intent();

        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "AAAAAAAAAAAAAAAAH"));
    }
}