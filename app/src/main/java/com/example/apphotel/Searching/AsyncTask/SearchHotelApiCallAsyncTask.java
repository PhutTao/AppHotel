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
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onApiCallSuccess(List<Hotel> hotels);
        void onApiCallFailure(String errorMessage);
    }

    public SearchHotelApiCallAsyncTask(ApiCallListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Hotel> doInBackground(String... params) {
        if (params == null || params.length == 0) {
            return null; // Không có từ khóa tìm kiếm
        }

        String keyword = params[0]; // Lấy từ khóa từ tham số đầu tiên

        try {
            HotelApiService apiService = HotelRetrofitClient.getRetrofitInstance().create(HotelApiService.class);
            Call<HotelApiRespone> call = apiService.getSearchHotels(keyword);

            Response<HotelApiRespone> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData(); // Trả về danh sách khách sạn
            } else {
                return null; // Lỗi API
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Lỗi mạng
        }
    }

    @Override
    protected void onPostExecute(List<Hotel> hotels) {
        if (hotels != null) {
            listener.onApiCallSuccess(hotels);
        } else {
            listener.onApiCallFailure("Failed to fetch search results.");
        }
    }
}
