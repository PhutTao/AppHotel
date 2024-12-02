package com.example.apphotel.Homescreen.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Booking.Activity.BookingActivity;
import com.example.apphotel.Homescreen.HotelApiService.Home_Booked;
import com.example.apphotel.Homescreen.HotelApiService.Home_BookedApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.R;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
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
    private void cancelBooking(int bookingId) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("bookingId", bookingId);

        Home_HotelEndpoint endpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        Call<JsonObject> call = endpoint.cancelBooking(requestBody);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    boolean success = responseBody.get("success").getAsBoolean();
                    String message = responseBody.get("message").getAsString();

                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    if (success) {
                        // Xóa thành công, cập nhật danh sách
                        new FetchBookedHotelsTask().execute();
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi API: Không nhận được phản hồi hợp lệ.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
            Button btnCancel = itemView.findViewById(R.id.mybooking_btn_cancel);
            Button btnEdit = itemView.findViewById(R.id.mybooking_btn_edit);



            // Gán giá trị từ API
            tvName.setText(booked.getTen());
            tvLocation.setText(booked.getDiaChi());
            tvRate.setText(String.format(Locale.getDefault(), "%.1f", booked.getDanhGia()));
            tvReviewCount.setText(String.format(Locale.getDefault(), "(%d đánh giá)", booked.getSoLuongDanhGia()));
            tvPrice.setText(String.format(Locale.getDefault(), "$%.0f / ngày", booked.getGia()));
            tvCheckIn.setText(booked.getNgayCheckIn());
            tvCheckOut.setText(booked.getNgayCheckOut());

            if (booked.getHinh() != null && !booked.getHinh().isEmpty()) {
                Picasso.get()
                        .load(booked.getHinh())
                        .placeholder(R.drawable.homescreen_muongthanh)
                        .error(R.drawable.homescreen_meroda)
                        .into(imgHotel);
            } else {
                imgHotel.setImageResource(R.drawable.homescreen_muongthanh);
            }
            // Xử lý sự kiện nút hủy
            btnCancel.setOnClickListener(v -> {
                new android.app.AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận hủy")
                        .setMessage("Bạn có chắc chắn muốn hủy đặt phòng tại " + booked.getTen() + "?")
                        .setPositiveButton("Đồng ý", (dialog, which) -> cancelBooking(booked.getId()))
                        .setNegativeButton("Hủy", null)
                        .show();
            });
            btnEdit.setOnClickListener(v -> {
                new android.app.AlertDialog.Builder(getContext())
                        .setTitle("Xác nhận đặt")
                        .setMessage("Vui lòng đặt phòng mới và xóa phòng cũ đã đặt")
                        .setPositiveButton("Đồng ý", (dialog, which) -> {
                            // Chuyển sang BookingActivity khi người dùng nhấn "Đồng ý"
                            Intent intent = new Intent(getContext(), BookingActivity.class);

                            // Truyền dữ liệu cần thiết vào Intent (nếu có)
                            intent.putExtra("bookingId", booked.getId());
                            intent.putExtra("hotelName", booked.getTen());
                            intent.putExtra("location", booked.getDiaChi());
                            intent.putExtra("checkInDate", booked.getNgayCheckIn());
                            intent.putExtra("checkOutDate", booked.getNgayCheckOut());
                            intent.putExtra("price", booked.getGia());


                            // Bắt đầu BookingActivity
                            startActivity(intent);
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            });



            lnBookedHotel.addView(itemView);
        }
    }


}
