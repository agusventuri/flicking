package com.baccaventuri.flicking.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.baccaventuri.flicking.Models.Size;
import com.baccaventuri.flicking.Models.Sizes;
import com.baccaventuri.flicking.Models.Title;
import com.baccaventuri.flicking.PhotoAdapter;
import com.baccaventuri.flicking.ViewModels.AlbumViewModel;
import com.baccaventuri.flicking.ViewModels.PhotoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private Gson gson;
    private GalleryAdapter mGalleriesAdapter;
    private AlbumsAdapter mAlbumsAdapter;
    private PhotoAdapter mPhotoAdapter;
    private Toolbar toolBar;
    private PhotoViewModel mPhotoViewModel;
    private AlbumViewModel mAlbumViewModel;

    private String galery =
            "https://www.flickr.com/services/rest/?method=flickr.galleries.getList&api_key=6e69c76253dbd558d5bcb0e797676a69&user_id=36587311%40N08&continuation=0&short_limit=2&format=json&nojsoncallback=1";

    public void loadGalleriaUsuario(GalleryAdapter mGalleriesAdapter, Toolbar albumToolbar, FragmentActivity activity) {

        this.mGalleriesAdapter = mGalleriesAdapter;
        this.toolBar = albumToolbar;

        mAlbumViewModel = new ViewModelProvider(activity).get(AlbumViewModel.class);

        mAlbumViewModel.getAllAlbums().observe(activity, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable final List<Album> albums) {
                // Update the cached copy of the words in the adapter.
                mGalleriesAdapter.updateDataset(albums);
            }
        });

        List<Album> albums = mAlbumViewModel.getAllAlbums().getValue();

        if (albums != null) {
            for (Album album:albums) {
                if (album.getBitmap() == null) {
                    //fetchBipmap(album.getPrimary());
                    album.fetchBitmap(mGalleriesAdapter);
                }
            }
            mGalleriesAdapter.updateDataset(albums);
            mGalleriesAdapter.notifyDataSetChanged();
        } else {
            fetchGalleriaUsuario();
        }

    }

    public void loadPhotoset (AlbumsAdapter mAlbumsAdapter,Album album,Boolean orderByName,Boolean asc, Toolbar albumToolbar, FragmentActivity activity) {
        this.mAlbumsAdapter = mAlbumsAdapter;
        this.toolBar = albumToolbar;

        mPhotoViewModel = new ViewModelProvider(activity).get(PhotoViewModel.class);

        mPhotoViewModel.getAllPhotos(album.getId(),orderByName,asc).observe(activity, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable final List<Photo> photos) {
                mAlbumsAdapter.updateDataset(photos);
                mAlbumsAdapter.notifyDataSetChanged();

                for (Photo photo:photos) {
                    if (photo.getBitmap() == null) {
                        photo.fetchBitmap(mAlbumsAdapter);
                        //fetchBipmap(photo.getId());
                    }
                }
                mAlbumsAdapter.updateDataset(photos);
                mAlbumsAdapter.notifyDataSetChanged();
            }

        });

        //List<> mPhotoViewModel.getAllPhotos(album.getId(),orderByName,asc)

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
                //"72157628042948461" +
                album.getId()+
                "&user_id=36587311%40N08&media=photos&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetPhotosLoaded, onGetPhotosError);
        Flicking.getSharedQueue().add(request);
    }

    private void fetchGalleriaUsuario() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url =
                "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=6e69c76253dbd558d5bcb0e797676a69&user_id=36587311%40N08&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetAlbumsLoaded, onGetAlbumsError);
        Flicking.getSharedQueue().add(request);
    }

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


    private final Response.Listener<String> onGetPhotosLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JsonObject object = (JsonObject) new JsonParser().parse(response);

            //convierto el json de album que recibo con campo title con un string,
            //a un json con campo title como {content:"mytitle"}, que es como
            //está hecho el modelo album
            JsonObject objphotoset = object.getAsJsonObject("photoset");
            JsonElement objtit = objphotoset.get("title");
            Title titulo = new Title();
            titulo.setContent(objtit.toString());
            String tit = gson.toJson(titulo);
            JsonElement titjson = new JsonParser().parse(tit);
            objphotoset.add("title",titjson);

            //obtengo album object oon formato correcto
            Album album = gson.fromJson(objphotoset, Album.class);
            List<Photo> photos = album.getPhoto();
            mAlbumsAdapter.updateDataset(photos);

            for (Photo photo:photos) {
                //photo.fetchDates();
                photo.setPhotoset(album.getId());
                mPhotoViewModel.insert(photo);

                if (photo.getBitmap() == null) {
                    //fetchBipmap(photo.getId());
                    photo.fetchBitmap(mAlbumsAdapter);
                }
            }
            toolBar.setTitle(album.getTitle());
        }
    };

    private final Response.ErrorListener onGetPhotosError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private final Response.Listener<String> onGetAlbumsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            JsonObject object = (JsonObject) new JsonParser().parse(response);
            JsonElement object1 = object.get("photosets");

            Gallery gallery = gson.fromJson(object1, Gallery.class);

            List<Album> albums = gallery.getPhotoset();
            mGalleriesAdapter.updateDataset(albums);

            for (Album album:albums) {
                mAlbumViewModel.insert(album);

                if (album.getBitmap() == null) {
                    //fetchBipmap(album.getPrimary());
                    album.fetchBitmap(mGalleriesAdapter);
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
        }
    };

    private final Response.ErrorListener onGetCommentsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    //CARGA DE SIZES
    public void fetchBipmap(String id) {

        StringBuilder url = new StringBuilder();
        url.append("https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=");
        url.append("6e69c76253dbd558d5bcb0e797676a69");
        url.append("&photo_id=");
        url.append(id);
        url.append("&format=json&nojsoncallback=1");
        String a;
        a= "https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=6e69c76253dbd558d5bcb0e797676a69&photo_id=6895430587&format=json&nojsoncallback=1";

        StringRequest request = new StringRequest(Request.Method.GET, url.toString(), onGetSizesLoaded, onGetSizesError);
        Flicking.getSharedQueue().add(request);
    }

    private final Response.Listener<String> onGetSizesLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            Sizes sizes = gson.fromJson(response, Sizes.class);
            List<Size> size = sizes.getSizes().getSize();

            String url = size.get(6).getSource();

            ImageLoader imageLoader = Flicking.getImageLoader();
            imageLoader.get(url, new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    assert true;
                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        Bitmap bitmap = response.getBitmap();
                        //if (opcion == "galeria"){
                        //    mGalleriesAdapter.notifyDataSetChanged();
                        //}else{
                            mAlbumsAdapter.notifyDataSetChanged();
                        //}

                    }
                }
            });
        }
    };


    private final Response.ErrorListener onGetSizesError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            assert true;
        }
    };

    /*public void showPrimaryImage(Gallery gallery, ImageView view){
        String photoId = gallery.getPages();

        StringBuilder url = new StringBuilder();
        url.append("https://www.flickr.com/services/rest/?method=flickr.photos.getSizes&api_key=");
        url.append("6e69c76253dbd558d5bcb0e797676a69");
        url.append("&photo_id=");
        url.append(photoId);
        url.append("&format=json&nojsoncallback=1");


        ImageLoader imageLoader = VolleyCatcher.getImgLoader();
        imageLoader.get(url.toString(), new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                view.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }*/
}
