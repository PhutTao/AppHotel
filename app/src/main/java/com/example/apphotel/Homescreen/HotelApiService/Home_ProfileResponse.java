package com.example.apphotel.Homescreen.HotelApiService;

public class Home_ProfileResponse {
    private String message;
    private Home_User data;
    private String status;

    public Home_ProfileResponse(String message, Home_User data, String status) {
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

    public Home_User getData() {
        return data;
    }

    public void setData(Home_User data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
