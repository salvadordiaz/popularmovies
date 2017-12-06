/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package fr.salvadordiaz.android.popularmovies.tmdb;

public class Movie {

    public final String original_title;
    private final String poster_path;

    @SuppressWarnings("unused"/*Used by json serialization/deserialization library*/)
    public Movie(String original_title, String poster_path) {
        this.original_title = original_title;
        this.poster_path = poster_path;
    }

    public String getPosterUrl() {
        return "http://image.tmdb.org/t/p/w185" + poster_path;
    }
}
