package com.baccaventuri.flicking;

import android.media.Image;

import java.util.ArrayList;

public class ItemAlbumes {
    private String name;
    private Image imagen;

    public ItemAlbumes(String name) {
        this.name = name;
       // this.imagen = image;
    }

    public String getName() {
        return name;
    }
    public Image getImage() {
        return imagen;
    }

    public static ArrayList<ItemAlbumes> createItemsList(int numItems) {
        ArrayList<ItemAlbumes> albumes = new ArrayList<ItemAlbumes>();

        for (int i = 1; i <= numItems; i++) {
            albumes.add(new ItemAlbumes("Album "+i));
        }

        return albumes;
    }
}


