package com.example.apphotel.Admin.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apphotel.Admin.Fragment.ManagerUserFragment;
import com.example.apphotel.databinding.ActivityAdminBinding;
import com.example.apphotel.Admin.Fragment.HomeFragment;
import com.example.apphotel.R;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            String selectedId =  item.getTitle().toString();
            System.out.println(selectedId);
            if (selectedId.equals("Trang Chủ")) {
                replaceFragment(new HomeFragment());
            } else if (selectedId.equals("Người Dùng")) {
                replaceFragment(new ManagerUserFragment());
            } else if (selectedId.equals("Khách Sạn")) {
                // Replace with appropriate fragment or action
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}