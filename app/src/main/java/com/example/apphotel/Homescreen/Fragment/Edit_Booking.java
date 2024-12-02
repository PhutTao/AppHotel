/*
package com.example.apphotel.Homescreen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Booking extends AppCompatActivity {

    private TextView datesSelect, guestsNumberSelect, roomTypeSelect;
    private EditText phoneNumberEdit;
    private CheckBox policy1Checkbox, policy2Checkbox;

    private int bookingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);

        // Ánh xạ các view
        datesSelect = findViewById(R.id.dates_select);
        guestsNumberSelect = findViewById(R.id.guests_number_select);
        roomTypeSelect = findViewById(R.id.room_type_select);
        phoneNumberEdit = findViewById(R.id.booking_phone_number);
        policy1Checkbox = findViewById(R.id.booking_checkbox_policy_1);
        policy2Checkbox = findViewById(R.id.booking_checkbox_policy_2);

        // Lấy dữ liệu từ Intent
        bookingId = getIntent().getIntExtra("bookingId", -1);
        datesSelect.setText(getIntent().getStringExtra("dates"));
        guestsNumberSelect.setText(getIntent().getStringExtra("guests"));
        roomTypeSelect.setText(getIntent().getStringExtra("roomType"));
        phoneNumberEdit.setText(getIntent().getStringExtra("phoneNumber"));

        // Xử lý sự kiện nút "Continue"
        findViewById(R.id.booking_continue_button).setOnClickListener(v -> updateBooking());
    }

    private void updateBooking() {
        // Tạo body request để gửi lên API
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("bookingId", bookingId);
        requestBody.addProperty("dates", datesSelect.getText().toString());
        requestBody.addProperty("guests", guestsNumberSelect.getText().toString());
        requestBody.addProperty("roomType", roomTypeSelect.getText().toString());
        requestBody.addProperty("phoneNumber", phoneNumberEdit.getText().toString());

        // Gọi API để cập nhật thông tin đặt phòng
        Home_HotelEndpoint endpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        Call<JsonObject> call = endpoint.updateBooking(requestBody);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean success = response.body().get("success").getAsBoolean();
                    String message = response.body().get("message").getAsString();

                    Toast.makeText(Edit_Booking.this, message, Toast.LENGTH_SHORT).show();
                    if (success) {
                        finish(); // Đóng màn hình khi cập nhật thành công
                    }
                } else {
                    Toast.makeText(Edit_Booking.this, "Lỗi cập nhật thông tin.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Edit_Booking.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
*/
