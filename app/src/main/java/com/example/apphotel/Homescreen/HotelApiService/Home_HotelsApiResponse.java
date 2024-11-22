package com.example.apphotel.Homescreen.HotelApiService;

import java.util.List;

public class Home_HotelsApiResponse {
    public Home_HotelsApiResponse(String message, List<Home_Hotel> data, String status) {
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

    public List<Home_Hotel> getData() {
        return data;
    }

    public void setData(List<Home_Hotel> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String message;
    private List<Home_Hotel> data;
    private String status;




}