package com.example.apphotel.Api;

import com.example.apphotel.Booking.Dto.BookingDto;
import com.example.apphotel.Homescreen.HotelApiService.Home_BookedApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiResponse;
import com.example.apphotel.Model.LoginResponse;
import com.example.apphotel.Model.RegisterResponse;
import com.example.apphotel.Searching.API.DetailHotelApiRespone;
import com.example.apphotel.Searching.API.HotelApiRespone;
import com.example.apphotel.Searching.API.ReviewHotelApiRespone;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    @GET("popular_hotels.php")
    Call<ApiResponse> getDetailHotel();
    @GET("popular_hotels.php")
    Call<ApiResponse> getReviewById();

    @GET("popular_hotels.php?id={id}") // Thay URL endpoint đúng
    Call<DetailHotelApiRespone> getHotelById(@Path("id") int hotelId, @Header("Authorization") String token);


    @POST("popular_hotels.php")
    Call<Void> postBooking(
            @Header("Authorization") String authorization,
            @Path("hotelId") int hotelId,
            @Body BookingDto bookingDto
    );

    @GET("popular_hotels.php")
    Call<ReviewHotelApiRespone> getReviewHotelById(
            @Header("Authorization") String token,
            @Path("id") int hotelId
    );
    @GET("popular_hotels.php")
    Call<HotelApiRespone> getAllHotels(@Header("Authorization") String token);



    @GET("popular_hotels.php")
    Call<HotelApiRespone> getAllPopularHotels(@Header("Authorization") String token);

    @GET("popular_hotels.php")
    Call<HotelApiRespone> getSearchHotels(
            @Header("Authorization") String token,
            @Query("keyword") String keyword


    );
    @GET("booked.php")
    Call<Home_HotelApiResponse> getHotel();
    @GET("booked.php")
    Call<Home_BookedApiResponse> getBooked();





}
