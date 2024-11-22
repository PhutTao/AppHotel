package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class Home_Hotel {
    public Home_Hotel(int id, String name, String address, double gia, List<Home_ImageDetail> imageDetails, int reviewQuantity, double rate, boolean isFavourited, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gia = gia;
        this.imageDetails = imageDetails;
        this.reviewQuantity = reviewQuantity;
        this.rate = rate;
        this.isFavourited = isFavourited;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public List<Home_ImageDetail> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<Home_ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }

    public int getReviewQuantity() {
        return reviewQuantity;
    }

    public void setReviewQuantity(int reviewQuantity) {
        this.reviewQuantity = reviewQuantity;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isFavourited() {
        return isFavourited;
    }

    public void setFavourited(boolean favourited) {
        isFavourited = favourited;
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

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("price")
    private double gia;

    @SerializedName("hinhAnh")
    private List<Home_ImageDetail> imageDetails;
    @SerializedName("soLuongDanhGia")
    private int reviewQuantity;
    @SerializedName("rate")
    private double rate;
    @SerializedName("isFavourited")
    private boolean isFavourited;

    @SerializedName("startDate")
    private Date startDate;
    @SerializedName("endDate")
    private Date endDate;

}

