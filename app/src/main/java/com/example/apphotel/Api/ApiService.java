package com.example.apphotel.Api;

import com.example.apphotel.Model.LoginResponse;
import com.example.apphotel.Model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("register.php")  // Đường dẫn của API (phần sau cùng URL)
    Call<RegisterResponse> registerUser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );
    // Đăng nhập
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password
    );
    @GET("popular_hotels.php")
    Call<ApiResponse> getPopularHotels();
    @GET("popular_hotels.php")
    Call<ApiResponse> getNearbyHotels();



}
