package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Favourite_Hotel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @SerializedName("id")
    private int id;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("hotel_id")
    private int hotelId;
    @SerializedName("created_at")
    private String createDate;

    public Favourite_Hotel(){

    }
    public Favourite_Hotel(int id, int userId, int hotelId, String createDate) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
        this.createDate = createDate;
    }
}
