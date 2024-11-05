package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Home_Booked {
    @SerializedName("id")
    private int id;
    @SerializedName("startDate")
    private Date startDate;
    @SerializedName("endDate")
    private Date endDate;
    @SerializedName("hotelRate")
    private double hotelRate;
    @SerializedName("reviewQuantity")
    private int reviewQuantity;
    @SerializedName("hotelId")
    private int hotelId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getHotelRate() {
        return hotelRate;
    }

    public void setHotelRate(double hotelRate) {
        this.hotelRate = hotelRate;
    }

    public int getReviewQuantity() {
        return reviewQuantity;
    }

    public void setReviewQuantity(int reviewQuantity) {
        this.reviewQuantity = reviewQuantity;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}
