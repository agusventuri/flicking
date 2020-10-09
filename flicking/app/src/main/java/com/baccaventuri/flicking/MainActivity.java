package com.baccaventuri.flicking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baccaventuri.flicking.ui.main.AlbumView;
import com.baccaventuri.flicking.ui.main.PhotoView;
import com.baccaventuri.flicking.ui.main.SetsView;

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
        if (item.getTitle().equals("Filtro nombre")) {
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
                sortingByText = "Ordenado por fecha ascendente";
            } else {
                editor.putBoolean(SortPicsByDateAscKey,
                        !sharedpreferences.getBoolean(SortPicsByDateAscKey, false));
                sortingByText = !sharedpreferences.getBoolean(SortPicsByDateAscKey, false)
                        ? "Ordenado por fecha ascendente"
                        : "Ordenado por fecha descendente";
            }
        } else {
            // aca me esta pidiendo que filtre por nombre
            // si actualmente el filtro por nombre esta activo, cambio el orden de filtrado
            // sino, cambio el filtro a filtrado por nombre
            if (sharedpreferences.getBoolean(SortPicsByNameKey, false)) {
                editor.putBoolean(SortPicsByNameAscKey,
                        !sharedpreferences.getBoolean(SortPicsByNameAscKey, false));
                sortingByText = !sharedpreferences.getBoolean(SortPicsByNameAscKey, false)
                        ? "Ordenado por nombre ascendente"
                        : "Ordenado por nombre descendente";
            } else {
                editor.putBoolean(SortPicsByNameKey, true);
                editor.putBoolean(SortPicsByNameAscKey, true);
                sortingByText = "Ordenado por nombre ascendente";
            }
        }

        Toast.makeText(this, sortingByText, Toast.LENGTH_SHORT).show();

        // guardo los nuevos ajustes
        editor.apply();

    }
}