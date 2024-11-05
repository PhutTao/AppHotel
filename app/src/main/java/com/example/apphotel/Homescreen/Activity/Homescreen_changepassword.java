package com.example.apphotel.Homescreen.Activity;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.Homescreen.HotelApiService.Home_ChangePasswordRequest;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelsApiResponse;
import com.example.apphotel.R;
import com.google.android.material.textfield.TextInputEditText;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Homescreen_changepassword extends AppCompatActivity {
    ImageView changepassword_btn_back;
    TextInputEditText current_password, new_password, confirm_password;
    TextView btn_save,txtError;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_changepassword);
        changepassword_btn_back = findViewById(R.id.changepassword_btn_back);
        current_password = findViewById(R.id.changepassword_current_password);
        new_password = findViewById(R.id.changepassword_new_password);
        confirm_password = findViewById(R.id.changepassword_confirm_password);
        btn_save = findViewById(R.id.changepassword_btn_save);
        txtError = findViewById(R.id.changepassword_txt_error);
        handler = new Handler();


        changepassword_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from TextInputEditText
                String currentPassword = current_password.getText().toString();
                String newPassword = new_password.getText().toString();
                String confirmPassword = confirm_password.getText().toString();

                // Check if new password and confirm password match
                if (!newPassword.equals(confirmPassword)) {
                    txtError.setTextColor(Color.RED);
                    txtError.setText("New password and confirm password do not match");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError.setText("");
                        }
                    }, 5000);
                    return;
                }

                // Get JWT token from SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String jwtToken = sharedPreferences.getString("jwtKey", null);

                // Create API service
                Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);

                // Create request body
                Home_ChangePasswordRequest request = new Home_ChangePasswordRequest(currentPassword, newPassword);

                // Make API call
                Call<ResponseBody> call = hotelEndpoint.changePassword("Bearer " + jwtToken, request);



                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            txtError.setTextColor(Color.GREEN);
                            txtError.setText("Change password success.");
                            current_password.setText("");
                            new_password.setText("");
                            confirm_password.setText("");
                            confirm_password.clearFocus();
                            new_password.clearFocus();
                            current_password.clearFocus();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    txtError.setText("");
                                }
                            }, 5000);

                        } else {
                            try {
                                txtError.setTextColor(Color.RED);
                                txtError.setText("The current password is incorrect");
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        txtError.setText("");
                                    }
                                }, 5000);
                            } catch (Exception e) {
                                Toast.makeText(Homescreen_changepassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Homescreen_changepassword.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


}

