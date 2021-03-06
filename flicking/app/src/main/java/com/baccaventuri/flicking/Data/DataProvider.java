package com.baccaventuri.flicking.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Ignore;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.baccaventuri.flicking.AlbumsAdapter;
import com.baccaventuri.flicking.Flicking;
import com.baccaventuri.flicking.GalleryAdapter;
import com.baccaventuri.flicking.Models.Album;
import com.baccaventuri.flicking.Models.Comment;
import com.baccaventuri.flicking.Models.Gallery;
import com.baccaventuri.flicking.Models.Photo;
import com.baccaventuri.flicking.PhotoAdapter;
import com.baccaventuri.flicking.R;
import com.baccaventuri.flicking.ViewModels.AlbumViewModel;
import com.baccaventuri.flicking.ViewModels.PhotoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataProvider {

    private Gson gson;
    private GalleryAdapter mGalleriesAdapter;
    private AlbumsAdapter mAlbumsAdapter;
    private PhotoAdapter mPhotoAdapter;
    private Toolbar toolBar;
    private PhotoViewModel mPhotoViewModel;
    private AlbumViewModel mAlbumViewModel;
    SharedPreferences sharedpreferences;
    boolean refreshAll;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setSharedPreferences(SharedPreferences sharedpreferences) {
        this.sharedpreferences = sharedpreferences;
        this.refreshAll = sharedpreferences.getBoolean("RefreshAll", true);
    }

    public Context context;

    public void loadGalleriaUsuario(GalleryAdapter mGalleriesAdapter, Toolbar albumToolbar, FragmentActivity activity) {

        this.mGalleriesAdapter = mGalleriesAdapter;
        this.toolBar = albumToolbar;

        mAlbumViewModel = new ViewModelProvider(activity).get(AlbumViewModel.class);

        mAlbumViewModel.getAllAlbums().observe(activity, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable final List<Album> albums) {
                for (Album album:albums) {
                    if (album.getBitmap() == null) {
                        fetchBitmap(album);
                    }
                }
                if (mAlbumViewModel.isEmpty() || refreshAll) {
                    Toast.makeText(activity, activity.getString(R.string.downloadingGallery), Toast.LENGTH_SHORT).show();
                    fetchGalleriaUsuario();
                }
                mGalleriesAdapter.updateDataset(albums);
                mGalleriesAdapter.notifyDataSetChanged();
            }
        });
    }

    public void loadPhotoset (AlbumsAdapter mAlbumsAdapter,Album album,Boolean orderByName,Boolean asc, Toolbar albumToolbar, FragmentActivity activity) {
        this.mAlbumsAdapter = mAlbumsAdapter;
        this.toolBar = albumToolbar;

        mPhotoViewModel = new ViewModelProvider(activity).get(PhotoViewModel.class);

        mPhotoViewModel.getAllPhotos(album.getId(),orderByName,asc).observe(activity, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable final List<Photo> photos) {
                for (Photo photo:photos) {
                    if (photo.getBitmap() == null) {
                        fetchBitmap(photo);
                    }
                }

                if (mPhotoViewModel.isEmpty() || refreshAll) {
                    Toast.makeText(activity, activity.getString(R.string.downloadingAlbum), Toast.LENGTH_SHORT).show();
                    fetchPhotoset(album);
                }

                mAlbumsAdapter.updateDataset(photos);
                mAlbumsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void loadPhoto(PhotoAdapter mPhotoAdapter,Photo photo, FragmentActivity activity) {
        this.mPhotoAdapter = mPhotoAdapter;
        fetchCommentsPhoto(photo);
    }

    private void fetchPhotoset(Album album) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=6e69c76253dbd558d5bcb0e797676a69&photoset_id=" +
                album.getId()+
                "&user_id=36587311%40N08&media=photos&format=json&nojsoncallback=1";

        StringRequest request = new StringRequest(Request.Method.GET, url, onGetPhotosLoaded, onGetPhotosError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetPhotosLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JsonObject object = (JsonObject) new JsonParser().parse(response);
            JsonObject objphotoset = object.getAsJsonObject("photoset");
            Album album = gson.fromJson(objphotoset, Album.class);
            List<Photo> photos = album.getPhoto();

            for (Photo photo:photos) {
                fetchDates(photo);
                photo.setPhotoset(album.getId());

                if (photo.getBitmap() == null) {
                    fetchBitmap(photo);
                }
            }
        }
    };

    private final Response.ErrorListener onGetPhotosError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void fetchGalleriaUsuario() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url =
                "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=6e69c76253dbd558d5bcb0e797676a69&user_id=36587311%40N08&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetAlbumsLoaded, onGetAlbumsError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetAlbumsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JsonObject object = (JsonObject) new JsonParser().parse(response);
            JsonElement object1 = object.get("photosets");
            JsonObject object2 = object1.getAsJsonObject();
            JsonElement list= object2.get("photoset");
            JsonArray listaalb = list.getAsJsonArray();

            //convierto cada title de cada album, en un string,
            //se esta recibiendo un title asi: "title":{"_content":"Italy"}
            for (JsonElement album : listaalb) {
                JsonObject objalb = album.getAsJsonObject();
                JsonElement tit = objalb.get("title");
                JsonObject tit2 = tit.getAsJsonObject();
                JsonElement titulo = tit2.get("_content");
                objalb.remove("title");
                objalb.add("title",titulo);
            }

            Gallery gallery = gson.fromJson(object1, Gallery.class);

            List<Album> albums = gallery.getPhotoset();
            mGalleriesAdapter.updateDataset(albums);

            for (Album album:albums) {
                if (album.getBitmap() == null) {
                    fetchBitmap(album);
                }
            }
        }
    };

    private final Response.ErrorListener onGetAlbumsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void fetchCommentsPhoto(Photo photo) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url =
                "https://www.flickr.com/services/rest/?method=flickr.photos.comments.getList&api_key=6e69c76253dbd558d5bcb0e797676a69&" +
                        "photo_id="+photo.getId()+
                        "&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetCommentsLoaded, onGetCommentsError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetCommentsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Comment> comentarios = new ArrayList<>();

            JsonObject object = (JsonObject) new JsonParser().parse(response);
            JsonElement comments = object.get("comments");
            JsonObject commentObject = comments.getAsJsonObject();
            JsonArray comment = (JsonArray) commentObject.get("comment");

            for (JsonElement comm: comment) {
                Comment comentario = gson.fromJson(comm,Comment.class);
                if (comentario !=null){
                    comentarios.add(comentario);
                }
            }
            mPhotoAdapter.updateDataset(comentarios);
            mPhotoAdapter.notifyDataSetChanged();
        }
    };

    private final Response.ErrorListener onGetCommentsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    //carga de fechas
    public void fetchDates(Photo photo) {
        String url = "https://www.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=6e69c76253dbd558d5bcb0e797676a69&photo_id=" +
                photo.getId() +
                "&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetPhotoInfoLoaded, onGetPhotoInfoError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetPhotoInfoLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");

            JsonObject object = (JsonObject) new JsonParser().parse(response);
            JsonElement photo = object.get("photo");
            JsonObject photoobject = photo.getAsJsonObject();
            JsonElement dates = (JsonElement) photoobject.get("dates");
            String id = photoobject.get("id").toString().replace("\"", "");
            Date created = null;
            //2011-09-17 18:22:44
            try {
                String taken = dates.getAsJsonObject().get("taken").toString();
                created = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(taken.replace("\"", ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            mPhotoViewModel.update(created, id);
            mAlbumsAdapter.notifyDataSetChanged();
        }
    };

    private final Response.ErrorListener onGetPhotoInfoError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            assert true;
        }
    };

    //CARGA DE BITMAP
    public void fetchBitmap(Photo photo) {

        StringBuilder url = new StringBuilder();
        url.append("https://live.staticflickr.com/");
        url.append(photo.getServer());
        url.append("/");
        url.append(photo.getId());
        url.append("_");
        url.append(photo.getSecret());
        url.append(".jpg");

        ImageLoader imageLoader = Flicking.getImageLoader();
        imageLoader.get(url.toString(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assert true;
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    Bitmap bitmap = response.getBitmap();
                    File f = createCachedFile(photo.getId(), bitmap);
                    photo.setBitmapUri(Uri.fromFile(f).toString());
                    photo.setBitmap(bitmap);
                    mPhotoViewModel.insert(photo);
                    mAlbumsAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void fetchBitmap(Album album) {

        StringBuilder url = new StringBuilder();

        url.append("https://live.staticflickr.com/");
        url.append(album.getServer());
        url.append("/");
        url.append(album.getPrimary());
        url.append("_");
        url.append(album.getSecret());
        url.append(".jpg");

        ImageLoader imageLoader = Flicking.getImageLoader();
        imageLoader.get(url.toString(), new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                assert true;
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    Bitmap bitmap = response.getBitmap();
                    File f = createCachedFile(album.getId(), bitmap);
                    album.setUriPrimary(Uri.fromFile(f).toString());
                    album.setBitmap(bitmap);
                    Log.d("UURI", album.getUriPrimary());
                    mAlbumViewModel.insert(album);
                    mGalleriesAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Ignore
    public File createCachedFile(String fileName, Bitmap image) {

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = this.context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(bArr);
            fos.flush();
            fos.close();

            return new File(this.context.getFilesDir().getAbsolutePath(), fileName + ".jpg");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
