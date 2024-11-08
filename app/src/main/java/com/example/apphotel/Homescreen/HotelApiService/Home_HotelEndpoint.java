package com.example.apphotel.Homescreen.HotelApiService;

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
    @GET("hotel_booking_api/getHotels.php")
    Call<List<Home_Hotel>> getHotels();
    @GET("/api/v1/hotel")
    Call<Home_HotelsApiResponse> getHotels(@Header("Authorization") String authorization);
    @GET("/api/v1/hotel/popular")
    Call<Home_HotelsApiResponse> getPpHotels(@Header("Authorization") String authorization);
    @GET("/api/v1/favourite-hotel")
    Call<Home_HotelsApiResponse> getFavoriteHotels(@Header("Authorization") String authorization);
    @GET("/api/v1/booking")
    Call<Home_BookedApiResponse> getBooked(@Header("Authorization") String authorization);
    @GET("/api/v1/hotel/{id}")
    Call<Home_HotelApiResponse> getHotel(@Path("id") int hotelId, @Header("Authorization") String authorization);
    @POST("/api/v1/favourite-hotel/{hotelId}")
    Call<Home_HotelsApiResponse> postFavoriteHotels(@Path("hotelId") int hotelId, @Header("Authorization") String authorization);
    @DELETE("/api/v1/favourite-hotel/{hotelId}")
    Call<Home_HotelsApiResponse> deleteFavoriteHotels(@Path("hotelId") int hotelId, @Header("Authorization") String authorization);
    @PUT("/api/v1/auth/change-password")
    Call<ResponseBody> changePassword(@Header("Authorization") String authorization, @Body Home_ChangePasswordRequest request);
    @GET("/api/v1/user/profile/avatar")
    Call<ResponseBody> getUserAvatar(@Header("Authorization") String authorization);
    @Multipart
    @POST("/api/v1/user/profile/avatar")
    Call<ResponseBody> uploadUserAvatar(
            @Header("Authorization") String authorization,
            @Part MultipartBody.Part image
    );
    @GET("http://localhost/hotel_booking_api/profile.php")
    Call<Home_ProfileResponse> getUserInfo(@Header("Authorization") String authorization);
    @POST("http://10.0.2.2/hotel_booking_api/hotel_images")
    Call<Home_ProfileResponse> updateUserInfo(
            @Header("Authorization") String authorization,
            @Body Home_User updatedUserData
    );



}
