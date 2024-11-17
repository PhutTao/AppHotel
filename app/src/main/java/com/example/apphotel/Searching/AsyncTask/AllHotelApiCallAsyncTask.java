package com.example.apphotel.Searching.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.apphotel.Api.ApiResponse;
import com.example.apphotel.Api.ApiService;
import com.example.apphotel.Api.RetrofitClient;
import com.example.apphotel.Searching.API.HotelApiRespone;
import com.example.apphotel.Searching.API.HotelApiService;
import com.example.apphotel.Searching.API.HotelRetrofitClient;
import com.example.apphotel.Searching.Domain.Hotel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AllHotelApiCallAsyncTask extends AsyncTask<Void, Void, List<Hotel>> {
    private Context context;
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onGetAllHotelsCompleted(List<Hotel> hotels);
        void onGetAllHotelsFailure(String errorMessage);
    }

    public AllHotelApiCallAsyncTask(Context context, ApiCallListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Hotel> doInBackground(Void... voids) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = preferences.getString("jwtKey", null);
        if (authToken != null) {
            try {
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                Call<HotelApiRespone> call = apiService.getAllHotels("Bearer " + authToken);

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
            listener.onGetAllHotelsCompleted(hotels);
        } else {
            listener.onGetAllHotelsFailure("API call failed");
        }
    }
}
