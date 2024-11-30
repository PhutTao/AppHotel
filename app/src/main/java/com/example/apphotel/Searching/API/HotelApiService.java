package com.example.apphotel.Searching.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HotelApiService {
    @GET("Homescreen/popular_hotels.php") //done
    Call<HotelApiRespone> getAllHotels();

    @GET("Search/get_hotel_detail.php")//done
    Call<DetailHotelApiRespone> getHotelById(
            @Query("id") int hotelId
    );



    @GET("Homescreen/popular_hotels.php")//done
    Call<HotelApiRespone> getAllPopularHotels();

    @GET("Search/getSearchHotels.php")
    Call<HotelApiRespone> getSearchHotels(
            @Query("keyword") String keyword
    );

    @GET("Homescreen/get_bookings.php")
    Call<ReviewHotelApiRespone> getReviewHotelById(@Query("id") int hotelId);
}
