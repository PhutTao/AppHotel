package com.example.apphotel.Searching.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.apphotel.Searching.API.DetailHotelApiRespone;
import com.example.apphotel.Searching.API.HotelApiService;
import com.example.apphotel.Searching.API.HotelRetrofitClient;
import com.example.apphotel.Searching.Domain.Hotel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DetailHotelApiCallAsyncTask extends AsyncTask<Integer, Void, Hotel> {
    private Context context;
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onApiCallSuccess(Hotel hotel);
        void onApiCallFailure(String errorMessage);
    }

    public DetailHotelApiCallAsyncTask(Context context, ApiCallListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Hotel doInBackground(Integer... params) {
        int hotelId = params[0];

        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = preferences.getString("jwtKey", null);

        if (authToken != null) {
            try {
                HotelApiService apiService = HotelRetrofitClient.getRetrofitInstance().create(HotelApiService.class);
                Call<DetailHotelApiRespone> call = apiService.getHotelById("Bearer " + authToken, hotelId);

                Response<DetailHotelApiRespone> response = call.execute();
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
    protected void onPostExecute(Hotel hotel) {
        super.onPostExecute(hotel);

        if (hotel != null) {
            listener.onApiCallSuccess(hotel);
        } else {
            listener.onApiCallFailure("API call failed");
        }
    }
}
