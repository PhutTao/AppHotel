package com.example.apphotel.Booking.Api;

import com.example.apphotel.Booking.Dto.BookingDto;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingApiService {
    @POST("Booking/post_booking.php")
    Call<Void> postBookingWithoutToken(
            @Body BookingDto bookingDto
    );
    @POST("updateBooking.php")
    Call<JsonObject> updateBooking(@Body JsonObject requestBody);

}
