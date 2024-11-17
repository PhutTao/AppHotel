package com.example.apphotel.Searching.API;

import com.example.apphotel.Searching.Domain.Hotel;
import com.google.gson.annotations.SerializedName;

public class DetailHotelApiRespone {
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private Hotel data;

    public String getStatus() {
        return status;
    }

    public Hotel getData() {
        return data;
    }
}
