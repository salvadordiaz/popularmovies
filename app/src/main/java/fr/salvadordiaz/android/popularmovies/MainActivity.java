package fr.salvadordiaz.android.popularmovies;

import java.io.IOException;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.*;
import fr.salvadordiaz.android.popularmovies.tmdb.DiscoverQueryResult;
import fr.salvadordiaz.android.popularmovies.tmdb.Movie;
import okhttp3.*;

public class MainActivity
                extends AppCompatActivity
                implements AdapterView.OnItemSelectedListener, MoviePosterAdapter.MovieClickListener {

    private static final String popularSegment = "popular";
    private static final String ratingSegment = "top_rated";

    private TextView errorTextView;
    private ProgressBar loadingIndicator;
    private RecyclerView postersRecyclerView;
    private MoviePosterAdapter moviePosterAdapter;

    @Override
    public void onClick(Movie movie) {
        startActivity(new Intent(this, MovieDetailActivity.class)
                        .putExtra(getString(R.string.movie_extra), movie));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorTextView = findViewById(R.id.tv_error_message);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);

        moviePosterAdapter = new MoviePosterAdapter(this);
        postersRecyclerView = findViewById(R.id.rv_posters);
        postersRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        postersRecyclerView.setHasFixedSize(true);
        postersRecyclerView.setAdapter(moviePosterAdapter);
    }

    private void findMovies(String sortSelection) {
        showError(View.INVISIBLE, View.VISIBLE);
        new MovieDbAsyncTask().execute(new Uri.Builder()
                        .scheme("https")
                        .path("api.themoviedb.org/3/movie/")
                        .appendPath(sortSelection)
                        .appendQueryParameter("api_key", BuildConfig.API_KEY)
                        .build());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        findMovies(position == 0 ? popularSegment : ratingSegment);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void showError(int errorVisibility, int dataVisibility) {
        errorTextView.setVisibility(errorVisibility);
        postersRecyclerView.setVisibility(dataVisibility);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        MenuItem item = menu.findItem(R.id.sort_spinner);
        Spinner spinner = (Spinner) item.getActionView();
        ArrayAdapter<CharSequence> sortMenuArrayAdapter = ArrayAdapter.createFromResource(
                        this, R.array.sort_options, android.R.layout.simple_spinner_item);
        sortMenuArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sortMenuArrayAdapter);
        spinner.setOnItemSelectedListener(this);
        return true;
    }

    private class MovieDbAsyncTask extends AsyncTask<Uri, Void, DiscoverQueryResult> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected DiscoverQueryResult doInBackground(Uri... urls) {
            try {
                ResponseBody body = new OkHttpClient()
                                .newCall(new Request.Builder().url(urls[0].toString()).build())
                                .execute().body();
                return body != null ? parseData(body.string()) : null;
            } catch (Exception e) {
                Log.e(MainActivity.class.getName(), getString(R.string.error_message), e);
                return null;
            }
        }

        private DiscoverQueryResult parseData(String jsonData) throws IOException {
            Moshi moshi = new Moshi.Builder().build();
            JsonAdapter<DiscoverQueryResult> adapter = moshi.adapter(DiscoverQueryResult.class);
            return adapter.fromJson(jsonData);
        }

        @Override
        protected void onPostExecute(DiscoverQueryResult s) {
            loadingIndicator.setVisibility(View.INVISIBLE);
            if (s != null) {
                showError(View.INVISIBLE, View.VISIBLE);
                moviePosterAdapter.setMovies(s.results);
            } else {
                showError(View.VISIBLE, View.INVISIBLE);
            }
        }
    }
}
