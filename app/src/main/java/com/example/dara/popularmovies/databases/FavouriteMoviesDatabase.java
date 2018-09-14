package com.example.dara.popularmovies.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dara.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class FavouriteMoviesDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favouriteMovies";
    private static FavouriteMoviesDatabase databaseInstance;

    public static FavouriteMoviesDatabase getDatabaseInstance (Context context) {
        if (databaseInstance == null) {
            synchronized (LOCK) {
                databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        FavouriteMoviesDatabase.class, FavouriteMoviesDatabase.DATABASE_NAME )
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return databaseInstance;

    }

    public abstract MovieDao movieDao();
}
