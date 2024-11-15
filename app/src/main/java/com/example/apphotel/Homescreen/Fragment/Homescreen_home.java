package com.example.apphotel.Homescreen.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.apphotel.Api.ApiResponse;
import com.example.apphotel.Api.ApiService;
import com.example.apphotel.Api.RetrofitClient;
import com.example.apphotel.Homescreen.Adapter.Homescreen_NearbyhotelAdapter;
import com.example.apphotel.Homescreen.Adapter.Homescreen_PopularHotelAdapter;
import com.example.apphotel.Homescreen.Adapter.HotelAdapter;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Booked;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;
import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.Model.Hotel;
import com.example.apphotel.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homescreen_home extends Fragment {
    private RecyclerView recyclerViewPopular, recyclerViewNearby;
    private HotelAdapter popularHotelAdapter, nearbyHotelAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.homescreen_fragment_home, container, false);

        // Initialize RecyclerViews
        recyclerViewPopular = view.findViewById(R.id.home_lvpopularhotel);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewNearby = view.findViewById(R.id.home_lvNearbyHotel);
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Fetch and display data
        fetchPopularHotels();
        fetchNearbyHotels();

        return view;
    }

    private void fetchPopularHotels() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.getPopularHotels();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    List<Hotel> hotels = response.body().getData();
                    popularHotelAdapter = new HotelAdapter(hotels, R.layout.homescreen_item_popularhotel);
                    recyclerViewPopular.setAdapter(popularHotelAdapter);
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu Popular Hotels", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối Popular Hotels API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchNearbyHotels() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse> call = apiService.getNearbyHotels();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.body() != null && response.body().isSuccess()) {
                    List<Hotel> hotels = response.body().getData();
                    nearbyHotelAdapter = new HotelAdapter(hotels, R.layout.homescreen_item_nearbyhotel);
                    recyclerViewNearby.setAdapter(nearbyHotelAdapter);
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu Nearby Hotels", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối Nearby Hotels API", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
