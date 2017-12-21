package fr.salvadordiaz.android.popularmovies;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;
import fr.salvadordiaz.android.popularmovies.tmdb.Movie;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CollapsingToolbarLayout toolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener(this);

        TextView descriptionTextView = findViewById(R.id.tv_movie_description);
        TextView releaseDateTextView = findViewById(R.id.tv_release_date);
        RatingBar ratingBar = findViewById(R.id.rb_movie_rating);
        toolbarLayout = findViewById(R.id.toolbar_layout);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.movie_extra))) {
            Movie movie = intent.getParcelableExtra(getString(R.string.movie_extra));
            setTitle(movie.original_title);
            descriptionTextView.setText(movie.overview);
            releaseDateTextView.setText(getResources().getString(R.string.movie_release, movie.release_date));
            ratingBar.setRating(movie.vote_average / 2);
            drawToolbarBackground(movie);
        }
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show();
    }

    private void drawToolbarBackground(Movie movie) {
        final ImageView imageView = new ImageView(this);
        Resources resources = getResources();
        Picasso.with(this)
                        .load(movie.getPosterUrl())
                        .centerCrop()
                        .resize(resources.getDisplayMetrics().widthPixels, resources.getDimensionPixelSize(R.dimen.app_bar_height))
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                toolbarLayout.setBackground(imageView.getDrawable());
                            }

                            @Override
                            public void onError() {
                            }
                        });
    }
}
