package com.example.apphotel.Review.dto;

import com.google.gson.annotations.SerializedName;

public class ReviewRequest {
    @SerializedName("content")
    String content;
    @SerializedName("rate")
    float rate;

    public ReviewRequest(String content, float rate) {
        this.content = content;
        this.rate = rate;
    }
}
