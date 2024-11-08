package com.example.apphotel.Register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.Api.ApiService;
import com.example.apphotel.Api.RetrofitClient;

import com.example.apphotel.Login.LoginActivity;
import com.example.apphotel.Model.RegisterResponse;
import com.example.apphotel.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ImageView passwordImageView;
    private EditText passwordText;
    private EditText usernameEditText;
    private EditText emailEditText;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openRegister();

        // Khởi tạo ApiService từ RetrofitClient
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    private void openRegister() {
        setContentView(R.layout.sinup_layout);

        Button createBtn = findViewById(R.id.signup_create_btn);
        TextView textView = findViewById(R.id.signup_move_login);
        passwordText = findViewById(R.id.signup_password_text);
        passwordImageView = findViewById(R.id.signup_password_icon);
        usernameEditText = findViewById(R.id.signup_username);
        emailEditText = findViewById(R.id.signu_Email);

        // Xử lý sự kiện click cho nút đăng ký
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }




                // Gọi API đăng ký
                Call<RegisterResponse> call = apiService.registerUser(username, email, password);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse.isSuccess()) {
                                Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                        }

                    }


                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Chuyển đến màn hình đăng nhập
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
