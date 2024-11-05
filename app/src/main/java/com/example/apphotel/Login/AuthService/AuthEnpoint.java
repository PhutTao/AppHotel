package com.example.apphotel.Login.AuthService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthEnpoint {
    @POST("api/v1/auth/authentication")
    Call<AccessTokenJson> authenticate(
            @Body AuthenticationRequest authenticationRequest
    );
    @POST("api/v1/auth/refresh")
    Call<AccessTokenJson> refreshToken(@Header("Authorization") String authorization);
}

