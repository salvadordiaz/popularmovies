/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package fr.salvadordiaz.android.popularmovies;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.ImageView;
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
        holder.posterView.setImageResource(android.R.color.transparent);
        Picasso.with(holder.posterView.getContext())
                        .load(movies.get(position).getPosterUrl())
                        .placeholder(android.R.color.transparent)
                        .error(android.R.drawable.stat_notify_error)
                        .fit()
                        .into(holder.posterView);
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

        private final ImageView posterView;

        private MoviePosterViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.iv_poster);
        }
    }

}
