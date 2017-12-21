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

    private final MovieClickListener listener;
    private List<Movie> movies;

    MoviePosterAdapter(MovieClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MoviePosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviePosterViewHolder(
                        LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.movie_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviePosterViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.posterView.setImageResource(android.R.color.transparent);
        holder.movie = movie;
        Picasso.with(holder.posterView.getContext())
                        .load(movie.getPosterUrl())
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

    class MoviePosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView posterView;
        private Movie movie;

        private MoviePosterViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.iv_poster);
            posterView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(movie);
        }
    }

    interface MovieClickListener {
        void onClick(Movie movie);
    }
}
