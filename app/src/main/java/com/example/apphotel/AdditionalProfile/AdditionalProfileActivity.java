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
import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additional_profile_layout);

        openAdditionalProfile();
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


        String BASE_URL = getString(R.string.base_url);
        // Initialize Retrofit only once
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




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


                new SettingProfileAsyncTask( retrofit.create(ProfileEndpoint.class), new ProfileApiCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent= new Intent(AdditionalProfileActivity.this, HomescreenActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(AdditionalProfileActivity.this, "No Profile", Toast.LENGTH_SHORT).show();

                    }
                }, file)
                        .execute(
                                dateOfBirth.getText().toString(),
                                sex.toString(),
                                addressEditText.getText().toString(),
                                phoneEditText.getText().toString(),
                                jwt);


            }
        });


        uploadImg();
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