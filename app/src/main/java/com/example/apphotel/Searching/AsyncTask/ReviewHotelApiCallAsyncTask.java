package com.example.apphotel.Searching.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.apphotel.Searching.API.HotelApiService;
import com.example.apphotel.Searching.API.HotelRetrofitClient;
import com.example.apphotel.Searching.API.ReviewHotelApiRespone;
import com.example.apphotel.Searching.Domain.Review;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReviewHotelApiCallAsyncTask extends AsyncTask<Integer, Void, List<Review>> {
    private Context context;
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onApiCallSuccess(List<Review> reviews);
        void onApiCallFailure(String errorMessage);
    }

    public ReviewHotelApiCallAsyncTask(Context context, ApiCallListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Review> doInBackground(Integer... params) {
        int hotelId = params[0];

        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = preferences.getString("jwtKey", null);

        if (authToken != null) {
            try {
                HotelApiService apiService = HotelRetrofitClient.getRetrofitInstance().create(HotelApiService.class);
                Call<ReviewHotelApiRespone> call = apiService.getReviewHotelById("Bearer " + authToken, hotelId);

                Response<ReviewHotelApiRespone> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().getData();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        super.onPostExecute(reviews);
        if (reviews != null) {
            listener.onApiCallSuccess(reviews);
        }
        else {
            listener.onApiCallFailure("API Call Failed");
        }
    }
}
