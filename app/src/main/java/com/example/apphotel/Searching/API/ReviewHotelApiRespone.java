package com.example.apphotel.Searching.API;

import com.example.apphotel.Searching.Domain.Review;

import java.util.List;

public class ReviewHotelApiRespone {
    private String message;
    private List<Review> data;

    public ReviewHotelApiRespone(String message, List<Review> data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Review> getData() {
        return data;
    }

    public void setData(List<Review> data) {
        this.data = data;
    }
}
