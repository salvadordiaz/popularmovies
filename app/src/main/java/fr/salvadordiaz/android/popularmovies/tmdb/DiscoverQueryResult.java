/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package fr.salvadordiaz.android.popularmovies.tmdb;

import java.util.List;

public class DiscoverQueryResult {

    public final List<Movie> results;

    public DiscoverQueryResult(List<Movie> results) {
        this.results = results;
    }
}
