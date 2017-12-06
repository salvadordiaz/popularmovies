/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package fr.salvadordiaz.android.popularmovies;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import fr.salvadordiaz.android.popularmovies.tmdb.Movie;

public class MoviePosterAdapter extends RecyclerView.Adapter<MoviePosterAdapter.MoviePosterViewHolder> {

    private List<Movie> movies;

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviePosterViewHolder(
                        LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.movie_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        holder.movieTextView.setText(movies.get(position).title);
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    static class MoviePosterViewHolder extends RecyclerView.ViewHolder {

        private final TextView movieTextView;

        private MoviePosterViewHolder(View itemView) {
            super(itemView);
            movieTextView = itemView.findViewById(R.id.tv_poster_url);
        }
    }

}
