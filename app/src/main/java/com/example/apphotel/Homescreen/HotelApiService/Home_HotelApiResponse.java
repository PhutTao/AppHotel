package com.example.apphotel.Homescreen.HotelApiService;

public class Home_HotelApiResponse {
    private String message;
    private Home_Hotel data;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Home_Hotel getData() {
        return data;
    }

    public void setData(Home_Hotel data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
