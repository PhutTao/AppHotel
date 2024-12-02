package com.example.apphotel.Homescreen.HotelApiService;

import com.example.apphotel.Searching.Domain.Hotel;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Home_HotelEndpoint {



    @POST("Homescreen/push_heart.php")
    Call<Push_Heart> pushHeart( @Query("hotelId") int hotelId, @Query("userId") int userId);
    @GET("Homescreen/check_heart.php")
    Call<Check_Heart> checkHeart(
            @Query("userId") int userId,
            @Query("hotelId") int hotelId
    );
    @POST("fetch_bookings.php")
    Call<List<Home_Booked>> fetchBookings();
    @GET("Homescreen/popular_hotels.php")  /*done*/
    Call<List<Hotel>> getHotels();
    @GET("Homescreen/popular_hotels.php")   /*done*/
    Call<Home_HotelsApiResponse> getHotels(@Header("Authorization") String authorization);
    @GET("Homescreen/get_popular_hotels.php")   /*done*/
    Call<Home_HotelsApiResponse> getPopularHotels(@Header("Authorization") String authorization);
    @GET("Homescreen/get_favorite_hotels.php")
    Call<Favourite_Hotels_Api_Response> getFavoriteHotels(@Query("id") int userId);
    @GET("Homescreen/get_bookings.php")
    Call<Home_BookedApiResponse> getBooked(@Header("Authorization") String authorization);
    @POST("Homescreen/cancelBooking.php")
    @Headers("Content-Type: application/json")
    Call<JsonObject> cancelBooking(@Body JsonObject bookingId);



    @GET("Homescreen/get_detail_hotel.php")
    Call<Home_HotelApiResponse> getDetailHotel(
            @Query("id") int hotelId,
            @Header("Authorization") String authorization
    );
    @GET("Homescreen/updateBooking.php")
    Call<JsonObject> updateBooking(@Body JsonObject requestBody);


    @PUT("popular_hotels.php")
    Call<ResponseBody> changePassword(@Header("Authorization") String authorization, @Body Home_ChangePasswordRequest request);
    @GET("profile_user.php")
    Call<ResponseBody> getUserAvatar(@Header("Authorization") String authorization);
    @Multipart
    @POST("profile_user.php")  /*done*/
    Call<ResponseBody> uploadUserAvatar(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part image
    );
    @GET("profile_user.php")  /*done*/
    Call<Home_ProfileResponse> getUserInfo(@Header("Authorization") String authorization);
    @POST("profile_user.php")  /*done*/
    Call<Home_ProfileResponse> updateUserInfo(
            @Header("Authorization") String authorization,
            @Body Home_User updatedUserData
    );

}
