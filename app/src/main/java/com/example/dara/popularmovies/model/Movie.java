package com.example.dara.popularmovies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "favourite_movies")
public class Movie implements Parcelable {

    //Movie id which serves as primary key in Room
    @PrimaryKey(autoGenerate = true)
    private int id;

    //Title of movie
    @ColumnInfo(name = "title")
    private final String mTitle;

    //Movie poster path
    @ColumnInfo(name = "poster_url")
    private final String mPosterUrl;

    //Movie backdrop path
    @ColumnInfo(name = "backdrop_url")
    private final String mBackdropUrl;

    //Movie overview
    @ColumnInfo(name = "overview")
    private final String mOverview;

    //Movie release date
    @ColumnInfo(name = "release_date")
    private final String mReleaseDate;

    //Movie vote average
    @ColumnInfo(name = "vote_average")
    private final int mVoteAverage;

    //Constructor which creates a Movies object
    @Ignore
    public Movie(String title, String posterUrl, String backdropUrl, String overview, String releaseDate,
                 int voteAverage) {
        this.mTitle = title;
        this.mPosterUrl = posterUrl;
        this.mBackdropUrl = backdropUrl;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mVoteAverage = voteAverage;
    }

    //Constructor which Room uses to create a Movies object
    public Movie(int id, String title, String posterUrl, String backdropUrl, String overview, String releaseDate,
                 int voteAverage) {
        this.id = id;
        this.mTitle = title;
        this.mPosterUrl = posterUrl;
        this.mBackdropUrl = backdropUrl;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mVoteAverage = voteAverage;
    }

    private Movie(Parcel in) {
        mTitle = in.readString();
        mPosterUrl = in.readString();
        mBackdropUrl = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mVoteAverage = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterUrl() {
        return mPosterUrl;
    }

    public String getBackdropUrl () {
        return mBackdropUrl;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getVoteAverage() {
        return mVoteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mPosterUrl);
        parcel.writeString(mBackdropUrl);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeInt(mVoteAverage);

    }
}
