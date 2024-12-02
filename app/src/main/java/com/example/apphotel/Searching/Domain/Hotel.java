package com.example.apphotel.Searching.Domain;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("price")
    private double price;
    @SerializedName("overview")
    private String overview;
    @SerializedName("image_details")
    private List<ImageDetail> imageDetails;
    @SerializedName("soluongdanhgia")
    private int reviewQuantity;
    @SerializedName("is_favorited")
    private boolean isFavourited;
    @SerializedName("rate")
    private double rate;

    public Hotel(int id, String name, String address, double price, String overview, List<ImageDetail> imageDetails, int reviewQuantity, boolean isFavourited, double rate) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.price = price;
        this.overview = overview;
        this.imageDetails = imageDetails;
        this.reviewQuantity = reviewQuantity;
        this.isFavourited = isFavourited;
        this.rate = rate;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<ImageDetail> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }

    public int getReviewQuantity() {
        return reviewQuantity;
    }

    public void setReviewQuantity(int reviewQuantity) {
        this.reviewQuantity = reviewQuantity;
    }

    public boolean isFavourited() {
        return isFavourited;
    }

    public void setFavourited(boolean favourited) {
        isFavourited = favourited;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
