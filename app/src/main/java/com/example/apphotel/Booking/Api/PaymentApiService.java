package com.example.apphotel.Booking.Api;

import com.example.apphotel.Booking.Dto.BookingDto;
import com.example.apphotel.Booking.Dto.PaymentDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PaymentApiService {
    @GET("payment.php")
    Call<PaymentApiResponse> getAllPayments(@Header("Authorization") String token);

    @POST("payment.php")
    Call<Void> postPayment(
            @Header("Authorization") String authorization,
            @Body PaymentDto paymentDto
    );

    @DELETE("payment.php")
    Call<Void> deletePayment(
            @Header("Authorization") String authorization,
            @Path("paymentId") String paymentId
    );
}
