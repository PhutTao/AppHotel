package com.example.apphotel.Admin.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apphotel.Admin.Adapter.UserAdapter;
import com.example.apphotel.Admin.Entity.User;
import com.example.apphotel.Admin.Response.UserResponse;
import com.example.apphotel.Api.ApiService;
import com.example.apphotel.Api.RetrofitClient;
import com.example.apphotel.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerUserFragment extends Fragment {
    private ListView listView;
    private UserAdapter adapter;
    private Button viewDetailUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manager_user, container, false);

        // Find the ListView
        listView = rootView.findViewById(R.id.listUser);
        // Fetch users from API
        fetchUsers();

        return rootView;
    }
    private void fetchUsers() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<UserResponse> call = apiService.getAllUser();

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body().getData();
                    adapter = new UserAdapter(requireContext(), users, requireActivity().getSupportFragmentManager());
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void navigateToDetailsFragment() {
        // Create the new fragment instance
        Fragment detailsFragment = new UserDetailFragment();

        // Use FragmentManager to replace the current fragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, detailsFragment);
        fragmentTransaction.addToBackStack(null); // Add this transaction to the back stack
        fragmentTransaction.commit();
    }

}
