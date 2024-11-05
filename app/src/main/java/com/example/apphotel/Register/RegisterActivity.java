package com.example.apphotel.Register;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Login.Activity.LoginActivity;
import com.example.apphotel.R;

public class RegisterActivity extends AppCompatActivity {

    private ImageView passwordImageView;
    private EditText passwordText;
    private EditText usernameEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openRegister();
    }

    private void openRegister() {
        setContentView(R.layout.sinup_layout);

        Button createBtn = findViewById(R.id.signup_create_btn);
        TextView textView = findViewById(R.id.signup_move_login);
        passwordText = findViewById(R.id.signup_password_text);
        passwordImageView = findViewById(R.id.signup_password_icon);
        usernameEditText = findViewById(R.id.signup_username);
        emailEditText = findViewById(R.id.signu_Email);

        // Đặt sự kiện nhấn nút "Đăng ký" để hiển thị thông báo mà không thực hiện đăng ký
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "Chức năng đăng ký đã bị lược bỏ.", Toast.LENGTH_SHORT).show();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        passwordImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentDrawable = passwordImageView.getDrawable();
                if (currentDrawable != null && currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.signup_password_hide).getConstantState())) {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordImageView.setImageResource(R.drawable.signup_visible_icon);
                } else {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordImageView.setImageResource(R.drawable.signup_password_hide);
                }

                // Cập nhật lại nội dung EditText
                passwordText.setSelection(passwordText.length());
            }
        });
    }
}
