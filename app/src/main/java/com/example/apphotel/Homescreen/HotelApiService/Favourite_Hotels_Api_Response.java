package com.example.apphotel.Homescreen.HotelApiService;

import java.util.List;

public class Favourite_Hotels_Api_Response {
    public Favourite_Hotels_Api_Response(String message, List<Favourite_Hotel> data, String status) {
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

    public List<Favourite_Hotel> getData() {
        return data;
    }

    public void setData(List<Favourite_Hotel> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String message;
    private List<Favourite_Hotel> data;
    private String status;


}
