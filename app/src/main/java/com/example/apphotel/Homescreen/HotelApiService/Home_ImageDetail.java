package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

public class Home_ImageDetail {
    @SerializedName("http://10.0.2.2/hotel_booking_api/hotel_images")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

