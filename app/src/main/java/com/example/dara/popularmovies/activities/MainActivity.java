package com.example.dara.popularmovies.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dara.popularmovies.databases.FavouriteMoviesDatabase;
import com.example.dara.popularmovies.model.Movie;
import com.example.dara.popularmovies.model.MovieAdapter;
import com.example.dara.popularmovies.R;
import com.example.dara.popularmovies.utilities.MoviesAsyncTask;
import com.example.dara.popularmovies.utilities.MoviesJsonUtils;
import com.example.dara.popularmovies.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener,
        MoviesAsyncTask.OnTaskCompleted {

    private RecyclerView mRecyclerView;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ProgressBar mLoadingIndicator;

    private TextView mErrorMessageTextView;

    private List<Movie> mList;

    private MovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_movies);

        mErrorMessageTextView = findViewById(R.id.tv_error_message);

        mLoadingIndicator = findViewById(R.id.loading_indicator);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);

        mList = new ArrayList<>();

        mAdapter = new MovieAdapter(mList, this);

        GridLayoutManager layoutManager = new
                GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mAdapter);

        loadPopularMoviesData();

        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadPopularMoviesData();
                    }
                }
        );
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager)
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void loadPopularMoviesData() {
        if (isNetworkAvailable()) {
            URL moviesUrl = NetworkUtils.popularMoviesUrl();
            new MoviesAsyncTask(this, mLoadingIndicator).execute(moviesUrl);
        } else {
            showError();
            mErrorMessageTextView.setText(R.string.network_error_message);
        }
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private void loadTopRatedMoviesData() {
        if (isNetworkAvailable()) {
            URL moviesUrl = NetworkUtils.topRatedMoviesUrl();
            new MoviesAsyncTask(this, mLoadingIndicator).execute(moviesUrl);
        } else {
            showError();
            mErrorMessageTextView.setText(R.string.network_error_message);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showData() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.GONE);
    }

    private void showError() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskCompleted(String result) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (result != null && !result.equals("")) {
            mList = MoviesJsonUtils.extractMoviesFromJson(result);
            mAdapter = new MovieAdapter(mList, MainActivity.this);
            mRecyclerView.setAdapter(mAdapter);
            showData();
        } else {
            showError();
        }
    }

    @Override
    public void onItemClickListener(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                mSwipeRefreshLayout.setRefreshing(true);
            case R.id.action_sort_by_popularity:
                this.setTitle(getString(R.string.app_name));
                mRecyclerView.setAdapter(null);
                loadPopularMoviesData();
                return true;
            case R.id.action_sort_by_rating:
                this.setTitle(getString(R.string.top_rated_label));
                mRecyclerView.setAdapter(null);
                loadTopRatedMoviesData();
                return true;
            case R.id.action_show_favourites:
                Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
