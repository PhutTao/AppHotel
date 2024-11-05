package com.example.apphotel.Homescreen.HotelApiService;

import java.util.List;

public class Home_HotelsApiResponse {
    private String message;
    private List<Home_Hotel> data;
    private String status;



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

}