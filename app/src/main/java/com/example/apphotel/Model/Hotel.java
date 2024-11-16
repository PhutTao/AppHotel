package com.example.apphotel.Model;

import com.example.apphotel.Homescreen.HotelApiService.Home_ImageDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hotel {
    private int id;
    private String ten;
    private String diaChi;
    private String hinhAnh;
    private String details; // Thông tin chi tiết khách sạn
    private double price;   // Giá khách sạn
    private double rating;  // Đánh giá
    private int reviewCount; // Số lượng đánh giá
    @SerializedName("imageDetails")
    private List<Home_ImageDetail> imageDetails;
    @SerializedName("reviewQuantity")
    private int reviewQuantity;
    @SerializedName("isFavourited")
    private boolean isFavourited;

    public boolean isFavourited() {
        return isFavourited;
    }

    public void setFavourited(boolean favourited) {
        isFavourited = favourited;
    }

    // Getters and Setters
    public int getReviewQuantity() {
        return reviewQuantity;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getReviewCount() { return reviewCount; }
    public void setReviewCount(int reviewCount) { this.reviewCount = reviewCount; }
    public List<Home_ImageDetail> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<Home_ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }

}
