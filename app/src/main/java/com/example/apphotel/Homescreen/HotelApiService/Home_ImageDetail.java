package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

public class Home_ImageDetail {
    @SerializedName("img")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

