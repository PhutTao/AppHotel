package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

public class Home_ImageDetail {
    public Home_ImageDetail(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @SerializedName("hinhAnh")
    private String imageUrl;


}

