package com.baccaventuri.flicking.Data;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.NonNull;

import com.baccaventuri.flicking.Models.Photo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
abstract class FlickingRoomDatabase extends RoomDatabase {

    abstract PhotoDao photoDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile FlickingRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FlickingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FlickingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FlickingRoomDatabase.class, "flickr_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

