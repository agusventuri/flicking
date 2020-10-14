package com.baccaventuri.flicking;

import android.app.Activity;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.baccaventuri.flicking.Models.Photo;

import java.util.List;

class PhotoThread implements Runnable
{
    Activity activity;
    List<Photo> photos;
    public PhotoThread(Activity activity, List<Photo> photos)
    {
        this.activity = activity;
        this.photos = photos;
    }
    @Override
    public void run()
    {
        //perform heavy task here and finally update the UI with result this way -
        try
        {
            for (Photo photo: photos) {
                photo.fetchBitmap();
            }
            activity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    assert true;
                }
            });

        } catch (Exception e)
        {
            // TODO: handle exception
        }

    }
}