package com.example.apphotel.Searching.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.apphotel.Booking.Activity.BookingCheckoutActivity;
import com.example.apphotel.Searching.API.DetailHotelApiRespone;
import com.example.apphotel.Searching.API.HotelApiService;
import com.example.apphotel.Searching.API.HotelRetrofitClient;
import com.example.apphotel.Searching.Domain.Hotel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DetailHotelApiCallAsyncTask extends AsyncTask<Integer, Void, Hotel> {
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onApiCallSuccess(Hotel hotel);
        void onApiCallFailure(String errorMessage);
    }

    public DetailHotelApiCallAsyncTask(ApiCallListener listener) {
        this.listener = listener;
    }

    @Override
    protected Hotel doInBackground(Integer... params) {
        if (params == null || params.length == 0) {
            Log.e("DetailHotelApiCall", "Missing hotelId parameter");
            return null;
        }

        int hotelId = params[0];

        try {
            HotelApiService apiService = HotelRetrofitClient.getRetrofitInstance().create(HotelApiService.class);
            Response<DetailHotelApiRespone> response = apiService.getHotelById(hotelId).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData();
            } else {
                Log.e("DetailHotelApiCall", "API Error: " + response.code());
                return null;
            }
        } catch (IOException e) {
            Log.e("DetailHotelApiCall", "Network error", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Hotel hotel) {
        if (hotel != null) {
            listener.onApiCallSuccess(hotel);
        } else {
            listener.onApiCallFailure("Failed to fetch hotel details");
        }
    }
}
