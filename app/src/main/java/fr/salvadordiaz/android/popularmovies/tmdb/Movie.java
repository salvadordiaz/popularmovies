/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package fr.salvadordiaz.android.popularmovies.tmdb;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public final String original_title;
    private final String poster_path;
    public final String overview;
    public final String release_date;
    public final Float vote_average;

    @SuppressWarnings("unused"/*Used by json serialization/deserialization library*/)
    public Movie(String original_title, String poster_path, String overview,
                    String release_date, Float vote_average) {
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
    }

    private Movie(Parcel in) {
        original_title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        vote_average = in.readFloat();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(original_title);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeFloat(vote_average);
    }

    public String getPosterUrl() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }
}
