package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Home_Booked {
    @SerializedName("id")
    private int id;

    @SerializedName("NgayCheckIn")
    private Date startDate;

    @SerializedName("NgayCheckOut")
    private Date endDate;

    @SerializedName("DanhGia")
    private double hotelRate;

    @SerializedName("SoLuongDanhGia")
    private int reviewQuantity;

    @SerializedName("hotel_id")
    private int hotelId;

    @SerializedName("Ten")
    private String hotelName;

    @SerializedName("DiaChi")
    private String hotelAddress;

    @SerializedName("Gia")
    private double price;

    @SerializedName("Hinh")
    private String imageUrl;

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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelAddress() {
        return hotelAddress;
    }

    public void setHotelAddress(String hotelAddress) {
        this.hotelAddress = hotelAddress;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
