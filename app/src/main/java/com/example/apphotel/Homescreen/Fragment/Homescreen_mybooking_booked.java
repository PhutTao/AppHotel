package com.example.apphotel.Homescreen.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.apphotel.Homescreen.HotelApiService.Home_Booked;
import com.example.apphotel.Homescreen.HotelApiService.Home_BookedApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class Homescreen_mybooking_booked extends Fragment {
    private LinearLayout lnBookedHotel;
    private ProgressBar progressBar;
    private List<Home_Booked> bookedHotels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_mybooking_fragment_booked, container, false);

        lnBookedHotel = view.findViewById(R.id.lvBookedHotel);
        progressBar = view.findViewById(R.id.progressBar);

        // Gọi API để lấy danh sách khách sạn đã đặt
        new FetchBookedHotelsTask().execute();

        return view;
    }

    private class FetchBookedHotelsTask extends AsyncTask<Void, Void, List<Home_Booked>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Home_Booked> doInBackground(Void... voids) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtKey", null);

            Home_HotelEndpoint endpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
            Call<Home_BookedApiResponse> call = endpoint.getBooked("Bearer " + jwtToken);

            try {
                Response<Home_BookedApiResponse> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().getData();
                }
            } catch (IOException e) {
                Log.e("API Error", "Failed to fetch booked hotels: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Home_Booked> result) {
            progressBar.setVisibility(View.GONE);
            if (result != null) {
                bookedHotels.clear();
                bookedHotels.addAll(result);
                populateBookedHotels();
            } else {
                Log.e("API Error", "Failed to fetch booked hotels.");
            }
        }
    }

    private void populateBookedHotels() {
        lnBookedHotel.removeAllViews();

        for (Home_Booked booked : bookedHotels) {
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.homescreen_item_booked, lnBookedHotel, false);

            // Gán dữ liệu cho các view
            TextView tvName = itemView.findViewById(R.id.mybooking_name_booked);
            TextView tvLocation = itemView.findViewById(R.id.mybooking_location_booked);
            TextView tvRate = itemView.findViewById(R.id.mybooking_rate_booked);
            TextView tvReviewCount = itemView.findViewById(R.id.mybooking_SLdanhgia_booked);
            TextView tvPrice = itemView.findViewById(R.id.mybooking_price_booked);
            TextView tvCheckIn = itemView.findViewById(R.id.mybooking_ngaycheckin_booked);
            TextView tvCheckOut = itemView.findViewById(R.id.mybooking_ngaycheckout_booked);
            ImageView imgHotel = itemView.findViewById(R.id.mybooking_img_booked);

            // Gán giá trị cho các thành phần UI
            tvName.setText(booked.getHotelName());
            tvLocation.setText(booked.getHotelAddress());
            tvRate.setText(String.valueOf(booked.getHotelRate()));
            tvReviewCount.setText(String.valueOf(booked.getReviewQuantity()));
            tvPrice.setText(String.format("%.0f", booked.getPrice()));
            tvCheckIn.setText(booked.getStartDate() != null ? booked.getStartDate().toString() : "N/A");
            tvCheckOut.setText(booked.getEndDate() != null ? booked.getEndDate().toString() : "N/A");

// Load ảnh từ URL sử dụng Picasso
            Picasso.get()
                    .load(booked.getImageUrl()) // URL ảnh từ API
                    .into(imgHotel); // ImageView nơi hiển thị ảnh

            lnBookedHotel.addView(itemView);
        }
    }
}
