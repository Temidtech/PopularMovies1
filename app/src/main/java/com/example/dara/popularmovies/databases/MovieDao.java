package com.example.dara.popularmovies.databases;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dara.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favourite_movies")
    List<Movie> getFavouriteMovies();

    @Insert
    void addToFavourites(Movie movie);

    @Query("SELECT title FROM favourite_movies")
    List<String> getFavouritesTitles();

    @Delete
    void removeFromFavourites(Movie movie);
}
