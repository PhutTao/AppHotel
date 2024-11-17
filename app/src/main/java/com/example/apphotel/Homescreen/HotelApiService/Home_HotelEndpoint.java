package com.example.apphotel.Homescreen.HotelApiService;

import com.example.apphotel.Searching.Domain.Hotel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface Home_HotelEndpoint {
    @GET("popular_hotels.php")
    Call<List<Hotel>> getHotels();
    @GET("popular_hotels.php")
    Call<Home_HotelsApiResponse> getHotels(@Header("Authorization") String authorization);
    @GET("popular_hotels.php")
    Call<Home_HotelsApiResponse> getPpHotels(@Header("Authorization") String authorization);
    @GET("favourite_hotel.php")
    Call<Home_HotelsApiResponse> getFavoriteHotels(@Header("Authorization") String authorization);
    @GET("get_bookings.php")
    Call<Home_BookedApiResponse> getBooked(@Header("Authorization") String authorization);
    @GET("popular_hotels.php")
    Call<Home_HotelApiResponse> getHotel(@Path("id") int hotelId, @Header("Authorization") String authorization);
    @POST("favourite_hotel.php")
    Call<Home_HotelsApiResponse> postFavoriteHotels(@Path("hotelId") int hotelId, @Header("Authorization") String authorization);
    @DELETE("favourite_hotel.php")
    Call<Home_HotelsApiResponse> deleteFavoriteHotels(@Path("hotelId") int hotelId, @Header("Authorization") String authorization);
    @PUT("change_password.php")
    Call<ResponseBody> changePassword(@Header("Authorization") String authorization, @Body Home_ChangePasswordRequest request);
    @GET("/api/v1/user/profile/avatar")
    Call<ResponseBody> getUserAvatar(@Header("Authorization") String authorization);
    @Multipart
    @POST("/api/v1/user/profile/avatar")
    Call<ResponseBody> uploadUserAvatar(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part image
    );
    @GET("profile.php")
    Call<Home_ProfileResponse> getUserInfo(@Header("Authorization") String authorization);
    @POST("profile.php")
    Call<Home_ProfileResponse> updateUserInfo(
            @Header("Authorization") String authorization,
            @Body Home_User updatedUserData
    );

}
