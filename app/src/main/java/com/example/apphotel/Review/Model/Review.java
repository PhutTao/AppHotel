package com.example.apphotel.Review.Model;

import android.graphics.Bitmap;

public class Review {
    private String reviewerName;
    private float rating;
    private String reviewText;
    private Bitmap bitmap;

    private String username;
    public Review(String reviewerName, float rating, String reviewText) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public float getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

