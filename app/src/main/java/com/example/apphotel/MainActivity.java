package com.example.apphotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Searching.Domain.Hotel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Gọi API và nhận dữ liệu từ server
        Home_HotelEndpoint apiService = RetrofitClient.getRetrofitInstance().create(Home_HotelEndpoint.class);
        Call<List<Hotel>> call = apiService.getHotels();

        // Thực hiện yêu cầu mạng
        call.enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(Call<List<Hotel>> call, Response<List<Hotel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Hotel> hotels = response.body();
                    // Xử lý dữ liệu nhận được từ server
                    // Ví dụ: Hiển thị thông tin khách sạn trong một RecyclerView
                } else {
                    // Xử lý lỗi nếu API trả về thất bại
                    Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Hotel>> call, Throwable t) {
                // Xử lý khi có lỗi mạng
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
