package com.example.dara.popularmovies.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dara.popularmovies.R;
import com.example.dara.popularmovies.databases.FavouriteMoviesDatabase;
import com.example.dara.popularmovies.model.Movie;
import com.example.dara.popularmovies.model.MovieAdapter;

import java.util.List;

public class FavouritesActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        FavouriteMoviesDatabase mDatabase = FavouriteMoviesDatabase.getDatabaseInstance(getApplicationContext());


        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        List<Movie> mList = mDatabase.movieDao().getFavouriteMovies();

        MovieAdapter mAdapter = new MovieAdapter(mList, this);
        mAdapter.setFavouriteMovies(mList);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

    }

    @Override
    public void onItemClickListener(Movie movie) {

    }
}
