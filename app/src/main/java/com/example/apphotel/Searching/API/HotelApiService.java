package com.example.apphotel.Searching.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotelApiService {
    @GET("api/v1/hotel")
    Call<HotelApiRespone> getAllHotels(@Header("Authorization") String token);

    @GET("api/v1/hotel/{id}")
    Call<DetailHotelApiRespone> getHotelById(
            @Header("Authorization") String token,
            @Path("id") int hotelId
    );

    @GET("api/v1/hotel/popular")
    Call<HotelApiRespone> getAllPopularHotels(@Header("Authorization") String token);

    @GET("api/v1/hotel/search")
    Call<HotelApiRespone> getSearchHotels(
            @Header("Authorization") String token,
            @Query("keyword") String keyword
    );

    @GET("api/v1/review/{id}")
    Call<ReviewHotelApiRespone> getReviewHotelById(
            @Header("Authorization") String token,
            @Path("id") int hotelId
    );
}
