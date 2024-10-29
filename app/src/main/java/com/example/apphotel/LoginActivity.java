package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity; // Đảm bảo import đúng AppCompatActivity

public class LoginActivity extends AppCompatActivity { // Kế thừa AppCompatActivity

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Đúng cú pháp onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Kết nối với layout

        // Ánh xạ các view từ layout vào Java
        etEmail = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Xử lý sự kiện khi người dùng nhấn vào nút Login
        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (email.equals("admin@hotel.com") && password.equals("1234")) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                // Chuyển sang màn hình khác nếu cần
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển sang màn hình đăng ký khi nhấn vào Register
        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
