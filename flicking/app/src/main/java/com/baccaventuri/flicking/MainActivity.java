package com.baccaventuri.flicking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baccaventuri.flicking.ui.main.PhotoView;
import com.baccaventuri.flicking.ui.main.SetsView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Pepeeeee");
        setSupportActionBar(myToolbar);

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
}