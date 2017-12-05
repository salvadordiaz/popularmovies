/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package fr.salvadordiaz.android.popularmovies.tmdb;

public class Movie {

    public final String original_title;

    @SuppressWarnings("unused"/*Used by json serialization/deserialization library*/)
    public Movie(String original_title) {
        this.original_title = original_title;
    }
}
