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
    // Lấy tất cả các phương thức thanh toán
    @GET("Booking/get_payment_methods.php")
    Call<PaymentApiResponse> getAllPayments();


    // Thêm một phương thức thanh toán
    @POST("Booking/add_payment_method.php")
    Call<Void> postPayment(@Body PaymentDto paymentDto);

    // Xóa một phương thức thanh toán
    @DELETE("Booking/delete_payment_method.php")
    Call<Void> deletePayment(
            @Path("paymentId") String paymentId
    );
}
