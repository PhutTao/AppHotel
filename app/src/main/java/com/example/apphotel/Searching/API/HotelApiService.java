package com.example.apphotel.Searching.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotelApiService {
    @GET("hotel_detail.php")
    Call<HotelApiRespone> getAllHotels(@Header("Authorization") String token);

    @GET("hotel_detail.php")
    Call<DetailHotelApiRespone> getHotelById(
            @Header("Authorization") String token,
            @Path("id") int hotelId
    );

    @GET("hotel_detail.php")
    Call<HotelApiRespone> getAllPopularHotels(@Header("Authorization") String token);

    @GET("hotel_detail.php")
    Call<HotelApiRespone> getSearchHotels(
            @Header("Authorization") String token,
            @Query("keyword") String keyword
    );

    @GET("hotel_detail.php")
    Call<ReviewHotelApiRespone> getReviewHotelById(
            @Header("Authorization") String token,
            @Path("id") int hotelId
    );
}
