package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.Homescreen.HotelApiService.Home_Hotel;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_fragment_home); // Gắn giao diện cho MainActivity
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/") // Dùng 10.0.2.2 khi chạy trên Emulator Android
                .addConverterFactory(GsonConverterFactory.create())
                .build();


    }
}
