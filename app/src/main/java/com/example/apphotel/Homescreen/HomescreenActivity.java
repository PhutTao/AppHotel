package com.example.apphotel.Homescreen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.apphotel.Homescreen.Fragment.Homescreen_about_us;
import com.example.apphotel.Homescreen.Fragment.Homescreen_setting;
import com.example.apphotel.R;
import com.example.apphotel.Homescreen.Fragment.Homescreen_home;
import com.example.apphotel.Homescreen.Fragment.Homescreen_mybooking;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Stack;

public class HomescreenActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Homescreen_home homefragment = new Homescreen_home();
    Homescreen_mybooking mybookingfragment = new Homescreen_mybooking();
    Homescreen_about_us about_us = new Homescreen_about_us();
    Homescreen_setting setting = new Homescreen_setting();

    // Add a Stack to keep track of the opened fragments
    Stack<Integer> fragmentStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        bottomNavigationView = findViewById(R.id.homescreen_bottom_navigation);
        String navigateToFragment = getIntent().getStringExtra("navigateTo");
        if ("mybookingfragment".equals(navigateToFragment)) {
            bottomNavigationView.setSelectedItemId(R.id.btn_mybooking);
            getSupportFragmentManager().beginTransaction().replace(R.id.homescreen_containerr, mybookingfragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.homescreen_containerr, homefragment).commit();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                // Before replacing the fragment, check if the stack already contains the item
                if (!fragmentStack.contains(item.getItemId())) {
                    // If not, push the id of the current fragment to the stack
                    fragmentStack.push(item.getItemId());
                }

                if (item.getItemId() == R.id.btn_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.homescreen_containerr, homefragment).addToBackStack(null).commit();
                    return true;
                } else if (item.getItemId() == R.id.btn_mybooking) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.homescreen_containerr, mybookingfragment).addToBackStack(null).commit();
                    return true;
                } else if (item.getItemId() == R.id.btn_about_us) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.homescreen_containerr, about_us).addToBackStack(null).commit();
                    return true;
                } else if (item.getItemId() == R.id.btn_setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.homescreen_containerr, setting).addToBackStack(null).commit();
                    return true;
                }

                return false;
            }
        });
    }

    // Add a method to get the id of the previous fragment
    public int getPreviousFragmentId() {
        // Check if the stack is not empty before popping
        if (!fragmentStack.isEmpty()) {
            // Pop the current fragment from the stack
            fragmentStack.pop();
            // If the stack is not empty, return the id of the previous fragment
            if (!fragmentStack.isEmpty()) {
                return fragmentStack.peek();
            }
        }
        // If the stack is empty, return the id of the current fragment
        return R.id.btn_home; // or any default fragment id
    }
}
