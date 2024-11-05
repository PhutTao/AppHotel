package com.example.apphotel.Homescreen.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.R;

import android.widget.ImageView;


public class Homescreen_notifications extends AppCompatActivity {
    ImageView notifications_btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_notifications);
        notifications_btn_back = findViewById(R.id.notifications_btn_back);
        notifications_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
