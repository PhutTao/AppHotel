package com.example.apphotel.Admin.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apphotel.Admin.Entity.User;
import com.example.apphotel.Admin.Response.DetailUserResponse;
import com.example.apphotel.Api.ApiService;
import com.example.apphotel.Api.RetrofitClient;
import com.example.apphotel.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailFragment extends Fragment {
    private EditText edtHoTen, edtEmal, edtSdt, edtDiaChi;
    private RadioButton sexNam, sexNu;
    private Spinner spnRole;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_user, container, false);

        // Initialize UI components
        initViews(rootView);

        // Prepare role labels and attach adapter to Spinner
        setupRoleSpinner();

        // Load user details
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("id")) {
            int userId = bundle.getInt("id");
            loadUser(userId);
        }

        return rootView;
    }

    // Initialize UI components
    private void initViews(View rootView) {
        edtHoTen = rootView.findViewById(R.id.edtFullName);
        edtEmal = rootView.findViewById(R.id.edtEmail);
        edtSdt = rootView.findViewById(R.id.edtSdt);
        edtDiaChi = rootView.findViewById(R.id.edtDiaChi);
        sexNam = rootView.findViewById(R.id.sexNam);
        sexNu = rootView.findViewById(R.id.sexNu);
        spnRole = rootView.findViewById(R.id.role);
    }

    // Set up Spinner with role options
    private void setupRoleSpinner() {
        List<String> roleLabels = new ArrayList<>();
        roleLabels.add("Admin");
        roleLabels.add("Khách hàng");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                roleLabels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnRole.setAdapter(adapter);

        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = parent.getItemAtPosition(position).toString();
                int roleId = "Admin".equals(selectedRole) ? 2 : 1;
                updateUserRole(roleId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    // Load user details from API
    private void loadUser(int userId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<DetailUserResponse> call = apiService.getDetailUser(userId);

        call.enqueue(new Callback<DetailUserResponse>() {
            @Override
            public void onResponse(Call<DetailUserResponse> call, Response<DetailUserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    populateUserDetails(response.body().getData());
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch user details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DetailUserResponse> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Populate user details into the UI
    private void populateUserDetails(User user) {
        if (user == null) return;

        edtHoTen.setText(user.getFullName() != null ? user.getFullName() : "");
        edtEmal.setText(user.getEmail() != null ? user.getEmail() : "");
        edtSdt.setText(user.getPhone() != null ? user.getPhone() : "");
        edtDiaChi.setText(user.getAddress() != null ? user.getAddress() : "");

        // Set gender
        if ("male".equals(user.getSex())) {
            sexNam.setChecked(true);
            sexNu.setChecked(false);
        } else {
            sexNu.setChecked(true);
            sexNam.setChecked(false);
        }

        // Set role in Spinner
        String userRole = user.getRole() == 1 ? "Khách hàng" : "Admin";
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnRole.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(userRole);
            spnRole.setSelection(position);
        }
    }

    // Update user role via API
    private void updateUserRole(int roleId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Bundle bundle = getArguments();
        if (bundle == null || !bundle.containsKey("id")) return;

        int userId = bundle.getInt("id");
        Call<com.example.apphotel.Admin.Response.Response> call = apiService.changeRole(userId, roleId);

        call.enqueue(new Callback<com.example.apphotel.Admin.Response.Response>() {
            @Override
            public void onResponse(Call<com.example.apphotel.Admin.Response.Response> call, Response<com.example.apphotel.Admin.Response.Response> response) {
                if (response.isSuccessful() && response.body() != null) {
                   } else {
                     }
            }

            @Override
            public void onFailure(Call<com.example.apphotel.Admin.Response.Response> call, Throwable t) {
                Log.e("API_ERROR", t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
