package com.baccaventuri.flicking;

import java.util.ArrayList;

public class ItemAlbumes {
    private String name;

    public ItemAlbumes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<ItemAlbumes> createItemsList(int numItems) {
        ArrayList<ItemAlbumes> albumes = new ArrayList<>();

        for (int i = 1; i <= numItems; i++) {
            albumes.add(new ItemAlbumes("Album "+i));
        }

        return albumes;
    }
}


