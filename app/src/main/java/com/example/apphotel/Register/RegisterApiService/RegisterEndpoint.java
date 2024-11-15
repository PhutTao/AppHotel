package com.example.apphotel.Register.RegisterApiService;

import com.example.apphotel.Login.AuthService.AccessTokenJson;
import com.example.apphotel.Register.dto.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterEndpoint {

    @POST("register.php")
    Call<AccessTokenJson> signup(@Body RegisterRequest registerRequest);
}
