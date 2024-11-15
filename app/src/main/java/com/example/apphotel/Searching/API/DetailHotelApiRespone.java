package com.example.apphotel.Searching.API;

import com.example.apphotel.Searching.Domain.Hotel;

public class DetailHotelApiRespone {
    private String message;
    private Hotel data;

    public DetailHotelApiRespone(String message, Hotel data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Hotel getData() {
        return data;
    }

    public void setData(Hotel data) {
        this.data = data;
    }
}
