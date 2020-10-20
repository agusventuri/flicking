package com.baccaventuri.flicking.Data;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class VolleyCatcher extends Application {
    private static RequestQueue queue;
    private static ImageLoader imgLoader;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);

        imgLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            LruCache<String, Bitmap> lruCache = new LruCache<>(10);

            @Override
            public Bitmap getBitmap(String url) {
                return lruCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                lruCache.put(url, bitmap);
            }
        });
    }

    public static RequestQueue getQueue() {
        return queue;
    }

    public static ImageLoader getImgLoader() {
        return imgLoader;
    }
}