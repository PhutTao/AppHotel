package com.example.apphotel.Review.dto;

public class ReviewResponse {
    private Long id;
    private float rate;
    private String content;
    private String username;
    private byte[] avatarImg;

    public ReviewResponse() {
    }

    public ReviewResponse(Long id, float rate, String content, String username, byte[] avatarImg) {
        this.id = id;
        this.rate = rate;
        this.content = content;
        this.username = username;
        this.avatarImg = avatarImg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(byte[] avatarImg) {
        this.avatarImg = avatarImg;
    }
}
