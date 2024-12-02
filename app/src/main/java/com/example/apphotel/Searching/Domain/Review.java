package com.example.apphotel.Searching.Domain;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("id")
    private int id; // ID của review
    @SerializedName("hotel_id")
    private int hotelId; // ID khách sạn
    @SerializedName("rate")
    private float rate; // Đánh giá (sao)
    @SerializedName("content")
    private String content; // Nội dung đánh giá
    @SerializedName("username")
    private String username; // Tên người dùng
    @SerializedName("avatar_img")
    private String avatarImg; // Ảnh đại diện
    @SerializedName("created_at")
    private String createdAt; // Ngày tạo


    public Review(int id, int hotelId, float rate, String content, String username, String avatarImg, String createdAt) {
        this.id = id;
        this.hotelId = hotelId;
        this.rate = rate;
        this.content = content;
        this.username = username;
        this.avatarImg = avatarImg;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
