package com.example.apphotel.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Homescreen.HomescreenActivity;

import com.example.apphotel.Login.AuthService.AuthEnpoint;
import com.example.apphotel.Login.AuthService.AuthenticationCallback;

import com.example.apphotel.Login.Fragment.ForgotPasswordBottomSheetFragment;
import com.example.apphotel.Model.LoginResponse;
import com.example.apphotel.R;
import com.example.apphotel.Register.RegisterActivity;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  {

    private Button loginButton;
    private EditText passwordText;
    private EditText emailText;
    private ImageView passwordImageView;
    private CheckBox checkBox;
    private TextView forgotPasswordTextView;

    private String BASE_URL ;

    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_layout);
        BASE_URL= getString(R.string.base_url);
        // khoi tao retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //auth



        // layout
        checkBox = findViewById(R.id.signin_checkbox_id);
        TextView moveRegister = findViewById(R.id.signin_move_register);
        emailText = findViewById(R.id.signin_email);
        loginButton = findViewById(R.id.signin_login_btn);
        passwordText = findViewById(R.id.signin_password_text);
        passwordImageView = findViewById(R.id.signin_password_icon);




        // business logic

        //1. Nho mat khau va email
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("SaveCredential", Context.MODE_PRIVATE);
            String savedUsername = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "001");
            if (!TextUtils.isEmpty(savedUsername) && !TextUtils.isEmpty(savedPassword)) {
                emailText.setText(savedUsername);
                passwordText.setText(savedPassword);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        // 2. Chuyen trang Register
        moveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        // 3. Login
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = emailText.getText().toString().trim();
                String password = passwordText.getText().toString().trim();

                // Kiểm tra rỗng
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                AuthEnpoint authEndpoint = retrofit.create(AuthEnpoint.class);
                Call<LoginResponse> call = authEndpoint.login(username, password);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse.isSuccess()) {
                                successLogin();
                            } else {
                                Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Lỗi phản hồi từ server", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        Log.e("LoginError", t.getMessage());
                    }
                });
            }
        });





        //4. Hien-An password
        passwordImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentDrawable = passwordImageView.getDrawable();
                if (currentDrawable != null && !currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.signup_password_hide).getConstantState())) {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordImageView.setImageResource(R.drawable.signup_password_hide);
                } else {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordImageView.setImageResource(R.drawable.signup_visible_icon);
                }

            }
        });



        //5. Forgot password
        forgotPasswordTextView = findViewById(R.id.signin_forgot_password);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                String email = emailText.getText().toString();
                bundle.putString("email", email);
                ForgotPasswordBottomSheetFragment bottomSheetFragment = new ForgotPasswordBottomSheetFragment();
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });


    }

    private void successLogin() {
        Intent intent = new Intent(LoginActivity.this, HomescreenActivity.class);
        startActivity(intent);
    }



    private void showFailAuthentication() {
        Toast.makeText(this, "Email or password are incorrect.", Toast.LENGTH_SHORT).show();
    }



}