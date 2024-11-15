package com.example.apphotel.Homescreen.Activity;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.R;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        // Lấy dữ liệu từ Intent
        String hotelId = getIntent().getStringExtra("HOTEL_ID");
        String hotelName = getIntent().getStringExtra("HOTEL_NAME");
        String hotelDetails = getIntent().getStringExtra("HOTEL_DETAILS");

        // Hiển thị thông tin khách sạn
        TextView nameTextView = findViewById(R.id.detail_img_slider);
        TextView detailsTextView = findViewById(R.id.detail_tv_hotel_address);
        nameTextView.setText(hotelName);
        detailsTextView.setText(hotelDetails);

        // Thêm logic đặt chỗ tại đây
    }
}

