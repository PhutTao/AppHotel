package com.example.apphotel.Homescreen.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.HotelApiService.Home_ProfileResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_User;
import com.example.apphotel.R;
import com.example.apphotel.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Homescreen_myprofile extends AppCompatActivity {

    ImageButton btn_back;
    ImageView imgProfile;
    TextView btn_save, txtError, txtError_avt;
    EditText edt_firstname, edt_lastname, edt_email, edt_phone, edt_address;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private Handler handler;
    ProgressBar progressBar_1;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_myprofile);
        progressBar_1 = findViewById(R.id.progress_bar);

        btn_back = findViewById(R.id.myprofile_btn_back);
        imgProfile = findViewById(R.id.myprofile_imgacc);
        edt_firstname = findViewById(R.id.myprofile_firstname);
        edt_lastname = findViewById(R.id.myprofile_lastname);
        edt_email = findViewById(R.id.myprofile_email);
        edt_phone = findViewById(R.id.myprofile_phonenumber);
        edt_address = findViewById(R.id.myprofile_address);
        txtError = findViewById(R.id.myprofile_txt_error);
        txtError_avt = findViewById(R.id.myprofile_txt_error_avt);
        handler = new Handler();
        new LoadUserProfileTask().execute();

        btn_save = findViewById(R.id.myprofile_btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveChanges();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imgProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void saveChanges() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtKey", null);

        File imageFile = bitmapToFile(((BitmapDrawable) imgProfile.getDrawable()).getBitmap());

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        Call<ResponseBody> call = hotelEndpoint.uploadUserAvatar("Bearer " + jwtToken, body);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Xử lý khi post ảnh thành công
                    txtError_avt.setTextColor(Color.GREEN);
                    txtError_avt.setText("Avatar uploaded successfully.");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError_avt.setText("");
                        }
                    }, 5000);
                } else {
                    // Xử lý khi có lỗi khi post ảnh
                    txtError_avt.setTextColor(Color.RED);
                    txtError_avt.setText("Failed to upload avatar.");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError_avt.setText("");
                        }
                    }, 5000);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError_avt.setText("");
                        }
                    }, 5000);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý khi có lỗi
                txtError_avt.setTextColor(Color.RED);
                txtError_avt.setText("Image size does not match.");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtError_avt.setText("");
                    }
                }, 5000);
            }
        });


        // Retrieve user input from EditText fields
        String updatedFirstName = edt_firstname.getText().toString();
        String updatedLastName = edt_lastname.getText().toString();
        String updatedEmail = edt_email.getText().toString();
        String updatedPhone = edt_phone.getText().toString();
        String updatedAddress = edt_address.getText().toString();

        // Create a Home_User object with updated information
        Home_User updatedUserData = new Home_User();
        updatedUserData.setFirstname(updatedFirstName);
        updatedUserData.setLastname(updatedLastName);
        updatedUserData.setEmail(updatedEmail);
        updatedUserData.setPhone(updatedPhone);
        updatedUserData.setAddress(updatedAddress);

        // Update user profile information
        updateUserInfo(updatedUserData);
    }
    private File bitmapToFile(Bitmap bitmap) {
        File filesDir = getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, "avatar_image.jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageFile;
    }

    private void loadUserAvatar() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtKey", null);

        Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        Call<ResponseBody> avtcall = hotelEndpoint.getUserAvatar("Bearer " + jwtToken);

        avtcall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    imgProfile.setImageBitmap(bitmap);
                    progressBar_1.setVisibility(View.GONE);
                } else {
                    // Xử lý khi không tải được ảnh đại diện
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar_1.setVisibility(View.GONE);
                            imgProfile.setImageResource(R.drawable.profile_default_avatar);
                        }

                    }, 3000);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý khi có lỗi
            }
        });
    }
    private class LoadUserProfileTask extends AsyncTask<Void, Void, Home_ProfileResponse> {

        @Override
        protected Home_ProfileResponse doInBackground(Void... voids) {
            // Perform the network request to get user profile
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtKey", null);

            Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
            Call<Home_ProfileResponse> profilecall = hotelEndpoint.getUserInfo("Bearer " + jwtToken);

            try {
                Response<Home_ProfileResponse> response = profilecall.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Home_ProfileResponse profileResponse) {
            if (profileResponse != null) {
                // Update UI with the retrieved user profile data
                Home_User userData = profileResponse.getData();
                if (userData != null) {
                    edt_firstname.setText(userData.getFirstname());
                    edt_lastname.setText(userData.getLastname());
                    edt_email.setText(userData.getEmail());
                    edt_phone.setText(userData.getPhone());
                    edt_address.setText(userData.getAddress());

                     loadUserAvatar();
                }
            } else {
                // Handle error case
                Toast.makeText(Homescreen_myprofile.this, "Failed to load user profile", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void updateUserInfo(Home_User updatedUserData) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtKey", null);

        Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        Call<Home_ProfileResponse> call = hotelEndpoint.updateUserInfo("Bearer " + jwtToken, updatedUserData);

        call.enqueue(new Callback<Home_ProfileResponse>() {
            @Override
            public void onResponse(Call<Home_ProfileResponse> call, Response<Home_ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    txtError.setTextColor(Color.GREEN);
                    edt_firstname.clearFocus();
                    edt_lastname.clearFocus();
                    edt_email.clearFocus();
                    edt_phone.clearFocus();
                    edt_address.clearFocus();
                    txtError.setText("Changes saved successfully.");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError.setText("");
                        }
                    }, 5000);
                } else {
                    txtError.setTextColor(Color.GREEN);
                    txtError.setText("Failed to save changes.");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            txtError.setText("");
                        }
                    }, 5000);
                }
            }

            @Override
            public void onFailure(Call<Home_ProfileResponse> call, Throwable t) {
                txtError.setTextColor(Color.GREEN);
                txtError.setText("Failed to save changes.");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        txtError.setText("");
                    }
                }, 5000);
            }
        });
    }



}
