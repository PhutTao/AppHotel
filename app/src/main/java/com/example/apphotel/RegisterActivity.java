package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity; // Đảm bảo import AppCompatActivity

public class RegisterActivity extends AppCompatActivity { // Kế thừa AppCompatActivity

    private EditText etEmail, etPassword;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // Đúng cú pháp onCreate
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Kết nối với layout

        // Ánh xạ các view từ layout vào Java
        etEmail = findViewById(R.id.etEmailRegister);
        etPassword = findViewById(R.id.etPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        // Xử lý sự kiện khi nhấn nút Register
        btnRegister.setOnClickListener(view -> {
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty()) {
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                // Chuyển sang màn hình Login
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển về màn hình Login khi nhấn vào Login
        tvLogin.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
    }
}
