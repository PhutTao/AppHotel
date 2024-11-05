package com.example.apphotel.Homescreen.HotelApiService;

import java.util.List;

public class Home_BookedApiResponse {
    private String message;
    private List<Home_Booked> data;
    private String status;

    public Home_BookedApiResponse(String message, List<Home_Booked> data, String status) {
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

    public List<Home_Booked> getData() {
        return data;
    }

    public void setData(List<Home_Booked> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
