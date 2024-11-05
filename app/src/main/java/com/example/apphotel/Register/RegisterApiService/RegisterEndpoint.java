package com.example.apphotel.Register.RegisterApiService;

import com.example.apphotel.Login.AuthService.AccessTokenJson;
import com.example.apphotel.Register.dto.RegisterRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterEndpoint {
    @POST("hotel_booking_api/register.php")  // Thay đổi đường dẫn cho đúng với API của bạn
    Call<ResponseBody> signup(@Body RegisterRequest registerRequest);
}
