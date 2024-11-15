package com.example.apphotel.AdditionalProfile.profileApiService;

import com.example.apphotel.AdditionalProfile.Dto.ResponseData;
import com.example.apphotel.AdditionalProfile.Dto.UserProfile;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ProfileEndpoint {
    @POST("/api/v1/user/profile")
    Call<ResponseData> setProfile(@Header("Authorization") String authorization ,@Body UserProfile userProfile);

    @Multipart()
    @POST("/api/v1/user/profile/avatar")
    Call<ResponseData> setAvatar(@Header("Authorization") String authorization, @Part MultipartBody.Part img);
}
