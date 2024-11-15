package com.example.apphotel.Review.ApiService;

import com.example.apphotel.AdditionalProfile.Dto.ResponseData;
import com.example.apphotel.Review.dto.ReviewRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReviewEndpoint {
    @GET("/api/v1/review/{hotelId}")
    Call<ResponseData> getReview(@Header("authorization") String jwtToken, @Path("hotelId") Long hotelId);
    @POST("/api/v1/review/{hotelId}")
    Call<ResponseData> createReview(@Header("authorization") String jwtToken, @Path("hotelId") Long hotelId, @Body ReviewRequest reviewRequest);
}
