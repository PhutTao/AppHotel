package com.example.apphotel.Searching.AsyncTask;

import android.os.AsyncTask;

import com.example.apphotel.Searching.API.HotelApiRespone;
import com.example.apphotel.Searching.API.HotelApiService;
import com.example.apphotel.Searching.API.HotelRetrofitClient;
import com.example.apphotel.Searching.Domain.Hotel;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AllHotelApiCallAsyncTask extends AsyncTask<Void, Void, List<Hotel>> {
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onGetAllHotelsCompleted(List<Hotel> hotels);
        void onGetAllHotelsFailure(String errorMessage);
    }

    public AllHotelApiCallAsyncTask(ApiCallListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Hotel> doInBackground(Void... voids) {
        try {
            HotelApiService apiService = HotelRetrofitClient.getRetrofitInstance().create(HotelApiService.class);
            Call<HotelApiRespone> call = apiService.getAllHotels();
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
    }

    @Override
    protected void onPostExecute(List<Hotel> hotels) {
        if (hotels != null) {
            listener.onGetAllHotelsCompleted(hotels);
        } else {
            listener.onGetAllHotelsFailure("Failed to fetch high rating hotels");
        }
    }
}
