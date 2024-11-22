package com.example.apphotel.Searching.API;

import com.example.apphotel.Searching.Domain.Hotel;

import java.util.List;

public class HotelApiRespone {
    private String message;
    private List<Hotel> data;
    private String status;

    public HotelApiRespone(String message, List<Hotel> data, String status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Hotel> getData() {
        return data;
    }

    public void setData(List<Hotel> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
