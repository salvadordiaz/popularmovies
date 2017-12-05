package fr.salvadordiaz.android.popularmovies;

import java.io.IOException;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import fr.salvadordiaz.android.popularmovies.tmdb.DiscoverQueryResult;
import fr.salvadordiaz.android.popularmovies.tmdb.Movie;
import okhttp3.*;

public class MainActivity extends AppCompatActivity {

    private TextView posterUrlsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        posterUrlsTextView = findViewById(R.id.tv_poster_urls);
        findPopularMovies();
    }

    private void findPopularMovies() {
        Uri url = new Uri.Builder()
                        .scheme("https")
                        .path("api.themoviedb.org/3/discover/movie")
                        .appendQueryParameter("api_key", BuildConfig.API_KEY)
                        .appendQueryParameter("sort_by", "popularity.desc")
                        .build();
        new MovieDbAsyncTask().execute(url);
    }

    private class MovieDbAsyncTask extends AsyncTask<Uri, Void, DiscoverQueryResult> {
        @Override
        protected DiscoverQueryResult doInBackground(Uri... urls) {
            try {
                ResponseBody body = new OkHttpClient()
                                .newCall(new Request.Builder().url(urls[0].toString()).build())
                                .execute().body();
                return body != null ? parseData(body.string()) : null;
            } catch (Exception e) {
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
            if (s != null) {
                for (Movie result : s.results) {
                    posterUrlsTextView.append(result.original_title + "\n\n\n");
                }
            } else {
                posterUrlsTextView.setText(getString(R.string.find_movies_error));
            }
        }
    }
}
