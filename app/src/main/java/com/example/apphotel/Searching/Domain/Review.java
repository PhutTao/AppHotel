package com.example.apphotel.Searching.Domain;

public class Review {
    private int id;
    private float rate;
    private String content;
    private String username;
    private String avatarImg;

    public Review(int id, float rate, String content, String username, String avatarImg) {
        this.id = id;
        this.rate = rate;
        this.content = content;
        this.username = username;
        this.avatarImg = avatarImg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAvatarImg() {
        return avatarImg;
    }

    public void setAvatarImg(String avatarImg) {
        this.avatarImg = avatarImg;
    }
}
