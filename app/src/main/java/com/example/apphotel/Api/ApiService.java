package com.example.apphotel.Api;

import com.example.apphotel.Homescreen.HotelApiService.Home_ChangePasswordRequest;
import com.example.apphotel.Model.LoginResponse;
import com.example.apphotel.Model.RegisterResponse;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/register.php")  // Đường dẫn của API (phần sau cùng URL)
    Call<RegisterResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );
    // Đăng nhập
    @FormUrlEncoded
    @POST("auth/login.php")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
    @FormUrlEncoded
    @POST("auth/updateUser.php")
    Call<LoginResponse> updateUser(@Field("selectedDate") String selectedDate,
                                   @Field("address") String address,
                                   @Field("phone") String phone,
                                   @Field("sex") String sex,
                                   @Field("username") String username);


}
