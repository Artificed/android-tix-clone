package com.example.onlymovie.service;

import android.util.Log;

import com.example.onlymovie.models.Cast;
import com.example.onlymovie.models.Series;
import com.example.onlymovie.response.CreditResponse;
import com.example.onlymovie.response.SeriesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeriesService {
    private static final String API_KEY = "d87f651a6b4efe803d9bb8e7b6cc5871";

    public static void fetchTrendingSeries(final SeriesServiceCallback callback) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<SeriesResponse> call = apiService.getTrendingSeries(API_KEY);

        call.enqueue(new Callback<SeriesResponse>() {
            @Override
            public void onResponse(Call<SeriesResponse> call, Response<SeriesResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    List<Series> seriesList = response.body().getResults();
                    callback.onSuccess(seriesList);
                    Log.d("test", "data: " + seriesList);
                } else {
                    callback.onFailure("Error fetching series");
                }
            }

            @Override
            public void onFailure(Call<SeriesResponse> call, Throwable t) {
                callback.onFailure("Error fetching series");
            }
        });
    }

    public static void fetchSeriesDetails(Long seriesId, final SeriesDetailCallback callback) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<Series> call = apiService.getSeriesDetails(seriesId, API_KEY);

        call.enqueue(new Callback<Series>() {
            @Override
            public void onResponse(Call<Series> call, Response<Series> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("Error fetching series details");
                }
            }

            @Override
            public void onFailure(Call<Series> call, Throwable t) {
                callback.onFailure("Error fetching series details");
            }
        });
    }

    public static void fetchSeriesCredits(Long seriesId, final SeriesService.CreditServiceCallback callback) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<CreditResponse> call = apiService.getMovieCredits(seriesId, "d87f651a6b4efe803d9bb8e7b6cc5871");

        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cast> castList = response.body().getCast();

                    callback.onSuccess(castList);
                    Log.d("API Response", "First 5 Casts: " + castList);
                } else {
                    callback.onFailure("Error fetching movie credits");
                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                callback.onFailure("Error: " + t.getMessage());
            }
        });
    }

    public interface SeriesServiceCallback {
        void onSuccess(List<Series> series);
        void onFailure(String errorMessage);
    }

    public interface SeriesDetailCallback {
        void onSuccess(Series series);
        void onFailure(String errorMessage);
    }

    public interface CreditServiceCallback {
        void onSuccess(List<Cast> casts);
        void onFailure(String errorMessage);
    }
}


