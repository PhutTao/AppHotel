package com.example.apphotel.Searching.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.apphotel.Searching.API.HotelApiRespone;
import com.example.apphotel.Searching.API.HotelApiService;
import com.example.apphotel.Searching.API.HotelRetrofitClient;
import com.example.apphotel.Searching.Domain.Hotel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SearchHotelApiCallAsyncTask extends AsyncTask<String, Void, List<Hotel>> {
    private Context context;
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onApiCallSuccess(List<Hotel> hotels);
        void onApiCallFailure(String errorMessage);
    }

    public SearchHotelApiCallAsyncTask(Context context, ApiCallListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Hotel> doInBackground(String... params) {
        String keyword = params[0];

        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = preferences.getString("jwtKey", null);

        if (authToken != null) {
            try {
                HotelApiService apiService = HotelRetrofitClient.getRetrofitInstance().create(HotelApiService.class);
                Call<HotelApiRespone> call = apiService.getSearchHotels("Bearer " + authToken, keyword);

                Response<HotelApiRespone> response = call.execute();
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
    protected void onPostExecute(List<Hotel> hotels) {
        super.onPostExecute(hotels);
        if (hotels != null) {
            listener.onApiCallSuccess(hotels);
        } else {
            listener.onApiCallFailure("API call failed");
        }
    }
}
