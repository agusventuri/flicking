package com.baccaventuri.flicking.Data;

import android.graphics.Bitmap;
import android.widget.ImageView;

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
import com.baccaventuri.flicking.GalleriesAdapter;
import com.baccaventuri.flicking.Models.GaleriaUsuario;
import com.baccaventuri.flicking.Models.Gallery;
import com.baccaventuri.flicking.Models.Photo;
import com.baccaventuri.flicking.Models.Photoset;
import com.baccaventuri.flicking.Models.Photoset_;
import com.baccaventuri.flicking.Models.Photosets;
import com.baccaventuri.flicking.Models.Size;
import com.baccaventuri.flicking.Models.Sizes;
import com.baccaventuri.flicking.ViewModels.AlbumViewModel;
import com.baccaventuri.flicking.ViewModels.PhotoViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private Gson gson;
    private GalleriesAdapter mGalleriesAdapter;
    private AlbumsAdapter mAlbumsAdapter;
    private Toolbar albumToolbar;
    private PhotoViewModel mPhotoViewModel;
    private AlbumViewModel mAlbumViewModel;
    private String galery =
            "https://www.flickr.com/services/rest/?method=flickr.galleries.getList&api_key=6e69c76253dbd558d5bcb0e797676a69&user_id=36587311%40N08&continuation=0&short_limit=2&format=json&nojsoncallback=1";

    public void loadGalleriaUsuario(GalleriesAdapter mGalleriesAdapter, Toolbar albumToolbar, FragmentActivity activity) {
        this.mGalleriesAdapter = mGalleriesAdapter;
        this.albumToolbar = albumToolbar;

        mAlbumViewModel = new ViewModelProvider(activity).get(AlbumViewModel.class);

        mAlbumViewModel.getAllAlbums().observe(activity, new Observer<List<Photoset_>>() {
            @Override
            public void onChanged(@Nullable final List<Photoset_> albums) {
                // Update the cached copy of the words in the adapter.
                mGalleriesAdapter.updateDataset(albums);
            }
        });

        List<Photoset_> albums = mAlbumViewModel.getAllAlbums().getValue();

        if (albums != null) {
            for (Photoset_ album:albums) {
                if (album.getBitmap() == null) {
                    //photo.fetchBitmap(mAlbumsAdapter);
                    fetchBipmap(album.getPrimary());
                }
            }
            mGalleriesAdapter.updateDataset(albums);
            mGalleriesAdapter.notifyDataSetChanged();
        } else {
            fetchGalleriaUsuario();
        }

    }

    public void loadPhotoset (AlbumsAdapter mAlbumsAdapter, Toolbar albumToolbar, FragmentActivity activity) {
        this.mAlbumsAdapter = mAlbumsAdapter;
        this.albumToolbar = albumToolbar;

        mPhotoViewModel = new ViewModelProvider(activity).get(PhotoViewModel.class);

        mPhotoViewModel.getAllPhotos().observe(activity, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable final List<Photo> photos) {
                // Update the cached copy of the words in the adapter.
                mAlbumsAdapter.updateDataset(photos);
            }
        });

        List<Photo> photos = mPhotoViewModel.getAllPhotos().getValue();

        if (photos != null) {
            for (Photo photo:photos) {
                if (photo.getBitmap() == null) {
                    //photo.fetchBitmap(mAlbumsAdapter);
                    fetchBipmap(photo.getId());
                }
            }
            mAlbumsAdapter.updateDataset(photos);
            mAlbumsAdapter.notifyDataSetChanged();
        } else {
            fetchPhotoset();
        }

    }

    private void fetchPhotoset() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url = "https://www.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=6e69c76253dbd558d5bcb0e797676a69&photoset_id=72157628042948461&user_id=36587311%40N08&media=photos&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetPhotosLoaded, onGetPhotosError);
        Flicking.getSharedQueue().add(request);
    }

    private void fetchGalleriaUsuario() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        /*String url =
                "https://www.flickr.com/services/rest/?method=flickr.galleries.getInfo&api_key=6e69c76253dbd558d5bcb0e797676a69&gallery_id=6065-72157617483228192&format=json&nojsoncallback=1";
*/
        String url =
                "https://www.flickr.com/services/rest/?method=flickr.photosets.getList&api_key=6e69c76253dbd558d5bcb0e797676a69&user_id=36587311%40N08&format=json&nojsoncallback=1";
        StringRequest request = new StringRequest(Request.Method.GET, url, onGetAlbumsLoaded, onGetAlbumsError);
        Flicking.getSharedQueue().add(request);
    }


    private final Response.Listener<String> onGetPhotosLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Photoset photoset = gson.fromJson(response, Photoset.class);
            List<Photo> photos = photoset.getPhotoset().getPhoto();
            mAlbumsAdapter.updateDataset(photos);

            for (Photo photo:photos) {
                mPhotoViewModel.insert(photo);

                if (photo.getBitmap() == null) {
                    fetchBipmap(photo.getId());
                }
            }

            albumToolbar.setTitle(photoset.getPhotoset().getTitle());
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
            GaleriaUsuario galeria = gson.fromJson(response, GaleriaUsuario.class);
            Photosets photosets = galeria.getPhotosets();
            List<Photoset> albums = photosets.getPhotoset();
            List<Photoset_> albumsset = new ArrayList<Photoset_>();

            //obtengo una lista de los Photoset_ de cada Photoset
            for (Photoset album:albums) {
                albumsset.add(album.getPhotoset());
            }
            mGalleriesAdapter.updateDataset(albumsset);

            for (Photoset album:albums) {
                mAlbumViewModel.insert(album);

                if (album.getPhotoset().getBitmap() == null) {
                    fetchBipmap(album.getPhotoset().getId());
                }
            }

            albumToolbar.setTitle("Albumes de usuario");
        }
    };

    private final Response.ErrorListener onGetAlbumsError = new Response.ErrorListener() {
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
                        mGalleriesAdapter.notifyDataSetChanged();
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

    public void showPrimaryImage(Gallery gallery, ImageView view){
        String photoId = gallery.getPrimaryPhotoId();

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
    }
}
