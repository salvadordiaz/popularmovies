package fr.salvadordiaz.android.popularmovies;

import java.io.IOException;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import fr.salvadordiaz.android.popularmovies.tmdb.DiscoverQueryResult;
import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    private TextView errorTextView;
    private ProgressBar loadingIndicator;
    private RecyclerView postersRecyclerView;
    private MoviePosterAdapter moviePosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        errorTextView = findViewById(R.id.tv_error_message);
        loadingIndicator = findViewById(R.id.pb_loading_indicator);

        moviePosterAdapter = new MoviePosterAdapter();
        postersRecyclerView = findViewById(R.id.rv_posters);
        postersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        postersRecyclerView.setHasFixedSize(true);
        postersRecyclerView.setAdapter(moviePosterAdapter);
        findPopularMovies();
    }

    private void findPopularMovies() {
        showError(View.INVISIBLE, View.VISIBLE);
        Uri url = new Uri.Builder()
                        .scheme("https")
                        .path("api.themoviedb.org/3/discover/movie")
                        .appendQueryParameter("api_key", BuildConfig.API_KEY)
                        .appendQueryParameter("sort_by", "popularity.desc")
                        .build();
        new MovieDbAsyncTask().execute(url);
    }

    private void showError(int errorVisibility, int dataVisibility) {
        errorTextView.setVisibility(errorVisibility);
        postersRecyclerView.setVisibility(dataVisibility);
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
