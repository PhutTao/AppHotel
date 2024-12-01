package com.example.apphotel.AdditionalProfile;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.example.apphotel.AdditionalProfile.AsyncTask.SettingProfileAsyncTask;
import com.example.apphotel.AdditionalProfile.profileApiService.ProfileApiCallBack;
import com.example.apphotel.AdditionalProfile.profileApiService.ProfileEndpoint;
import com.example.apphotel.Api.ApiService;
import com.example.apphotel.Api.RetrofitClient;
import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.Login.LoginActivity;
import com.example.apphotel.Model.LoginResponse;
import com.example.apphotel.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdditionalProfileActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private String selectedDate="";
    private String sex="";
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private Button skipBtn;
    private  Button cpmBtn;
    EditText dateOfBirth;
    RadioGroup sexRadioGroup;
    EditText addressEditText;
    EditText phoneEditText;
    private Retrofit retrofit;
    private String username;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_profile_layout);
        openAdditionalProfile();
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
    }

    private void openAdditionalProfile() {
        Button calendarButton = findViewById(R.id.profile_calendarButton);
        //
        dateOfBirth=findViewById(R.id.profile_calendar_text);
        skipBtn=findViewById(R.id.profile_skip_btn);
        cpmBtn=findViewById(R.id.profile_complete_btn);
        imageView = findViewById(R.id.review_item_avatar);
        sexRadioGroup=findViewById(R.id.radioGroup);
        addressEditText=findViewById(R.id.profile_address);
        phoneEditText=findViewById(R.id.profile_phone_number);


        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=findViewById(checkedId);
                sex = (String) radioButton.getText();
            }
        });

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendarDialog(); // Gọi hàm để hiển thị lịch
            }
        });
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdditionalProfileActivity.this, HomescreenActivity.class);
                startActivity(intent);
            }
        });
        cpmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                String jwt=sharedPreferences.getString("jwtKey","");
                File directory = AdditionalProfileActivity.this.getFilesDir();
                File file = new File(directory, "avatar.jpg");
                if(Objects.nonNull(bitmap)){
                    try(OutputStream outputStream =  new FileOutputStream(file)){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                Intent intent = getIntent();
                username = intent.getStringExtra("username");
                selectedDate = dateOfBirth.getText().toString();
                String address = addressEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                updateUser(selectedDate,address,phone, sex, username);
            }
        });


        uploadImg();
    }
    private void updateUser(String selectedDate, String address, String phone, String sex, String username) {
        System.out.println(selectedDate);
        System.out.println(address);
        System.out.println(phone);
        System.out.println(sex);
        System.out.println(username);

        Call<LoginResponse> call = apiService.updateUser(selectedDate, address, phone, sex, username);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.isSuccess()) {
                        Toast.makeText(AdditionalProfileActivity.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AdditionalProfileActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(AdditionalProfileActivity.this, "Update failed: " + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AdditionalProfileActivity.this, "Server error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(AdditionalProfileActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showCalendarDialog() {
        Dialog calendarDialog = new Dialog(this); // 'this' tham chiếu đến Activity hiện tại
        calendarDialog.setContentView(R.layout.profile_calendar_dialog); // Sử dụng layout cho Dialog
        CalendarView calendarView = calendarDialog.findViewById(R.id.profile_calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                dateOfBirth.setText(selectedDate);
                calendarDialog.dismiss();
            }
        });
        calendarDialog.show();
    }
    private void uploadImg() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở giao diện chọn hình ảnh từ bộ nhớ hoặc máy ảnh
                openImageChooser();      }
        });
    }
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            try {
                 bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}