package com.example.dara.popularmovies.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dara.popularmovies.R;
import com.example.dara.popularmovies.databases.FavouriteMoviesDatabase;
import com.example.dara.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    // Extra for the movie ID to be received in the intent
    public static final String EXTRA_MOVIE_ID = "extraMovieId";

    private TextView mTitleTextView;

    private ImageView mMoviePoster;

    private ImageView mMovieBackdrop;

    private TextView mReleaseDate;

    private RatingBar mRating;

    private TextView mOverview;

    private ImageButton mFavouritesButton;

    private FavouriteMoviesDatabase mDatabase;

    private Movie mMovie;

    private View.OnClickListener mFavButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTitleTextView = findViewById(R.id.tv_movie_title);
        mMoviePoster = findViewById(R.id.movie_poster);
        mMovieBackdrop = findViewById(R.id.movie_backdrop);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRating = findViewById(R.id.tv_vote_average);
        mOverview = findViewById(R.id.tv_overview);
        mFavouritesButton = findViewById(R.id.favourites_button);

        mDatabase = FavouriteMoviesDatabase.getDatabaseInstance(getApplicationContext());

        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE_ID);

        mFavButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavourite(mMovie)) {
                    mFavouritesButton.setImageResource(R.drawable.baseline_star_border_black_36);
                    Toast.makeText(DetailActivity.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
                    mDatabase.movieDao().removeFromFavourites(mMovie);
                } else {
                    mFavouritesButton.setImageResource(R.drawable.baseline_star_black_36);
                    Toast.makeText(DetailActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
                    mDatabase.movieDao().addToFavourites(mMovie);
                }
            }
        };

        populateUI(mMovie);

        setUpFavourites();
    }

    private void setUpFavourites() {
        if (isFavourite(mMovie)) {
            mFavouritesButton.setImageResource(R.drawable.baseline_star_black_36);
        } else {
            mFavouritesButton.setImageResource(R.drawable.baseline_star_border_black_36);
        }
        mFavouritesButton.setOnClickListener(mFavButtonListener);
    }

    private boolean isFavourite(Movie movie) {
        List<String> favouriteMovies = mDatabase.movieDao().getFavouritesTitles();
        boolean isFav = false;
        for (String title : favouriteMovies) {
            if (title.equals(movie.getTitle())) {
                isFav = true;
            }
        }
        return isFav;
    }

    private void populateUI(Movie movie) {
        if (movie == null) return;

        String releaseDate = movie.getReleaseDate();
        String[] dateStrings = releaseDate.split("-");
        String releaseYear = dateStrings[0];

        mTitleTextView.setText(movie.getTitle());
        mReleaseDate.setText(releaseYear);
        mRating.setRating(movie.getVoteAverage());
        mOverview.setText(movie.getOverview());

        Picasso.get()
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.poster_error)
                .into(mMoviePoster);

        Picasso.get()
                .load(movie.getBackdropUrl())
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.poster_error)
                .into(mMovieBackdrop);
    }

}

