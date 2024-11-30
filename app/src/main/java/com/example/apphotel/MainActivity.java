package com.example.apphotel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.apphotel.Homescreen.Fragment.Homescreen_home;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String navigateTo = getIntent().getStringExtra("navigateTo");
        if ("Homescreen_home".equals(navigateTo)) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new Homescreen_home())
                    .commit();
        }
    }
}