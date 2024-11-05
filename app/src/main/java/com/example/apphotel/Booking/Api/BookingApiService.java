package com.example.apphotel.Booking.Api;

import com.example.apphotel.Booking.Dto.BookingDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingApiService {
    @POST("/api/v1/booking/{hotelId}")
    Call<Void> postBooking(
            @Header("Authorization") String authorization,
            @Path("hotelId") int hotelId,
            @Body BookingDto bookingDto
    );
}
