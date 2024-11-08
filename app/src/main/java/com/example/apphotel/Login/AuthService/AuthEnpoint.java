package com.example.apphotel.Login.AuthService;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AuthEnpoint {
    @POST("login.php")
    Call<AccessTokenJson> authenticate(
            @Body AuthenticationRequest authenticationRequest
    );
    @POST("login.php")
    Call<AccessTokenJson> refreshToken(@Header("Authorization") String authorization);

}

