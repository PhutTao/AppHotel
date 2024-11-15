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

import com.example.apphotel.AdditionalProfile.AdditionalProfileActivity;
import com.example.apphotel.Login.Activity.LoginActivity;
import com.example.apphotel.R;
import com.example.apphotel.Register.AsynTask.RegisterAsynTask;
import com.example.apphotel.Register.RegisterApiService.RegisterCallBack;
import com.example.apphotel.Register.RegisterApiService.RegisterEndpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private ImageView passwordImageView;
    private EditText passwordText;
    private EditText usernameEditText;
    private  EditText emailEditText;
    private Retrofit  retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openRegister();

    }

    private void openRegister() {
        setContentView(R.layout.sinup_layout);

       String BASE_URL = getString(R.string.base_url);
        // Initialize Retrofit only once
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        Button createBtn=findViewById(R.id.signup_create_btn);
        TextView textView=findViewById(R.id.signup_move_login);
        passwordText=findViewById(R.id.signup_password_text);
        passwordImageView=findViewById(R.id.signup_password_icon);
        usernameEditText=findViewById(R.id.signup_username);
        emailEditText=findViewById(R.id.signu_Email);



        ///
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, " Register ", Toast.LENGTH_SHORT).show();

                String password=passwordText.getText().toString();
                String email=emailEditText.getText().toString();
                String username=usernameEditText.getText().toString();
                new RegisterAsynTask(RegisterActivity.this, (RegisterEndpoint) retrofit.create(RegisterEndpoint.class), new RegisterCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent=new Intent(RegisterActivity.this, AdditionalProfileActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(RegisterActivity.this, " Register Information are incorrect", Toast.LENGTH_SHORT).show();
                    }
                })
                        .execute(email,password,username)
                ;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });



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

    }
}