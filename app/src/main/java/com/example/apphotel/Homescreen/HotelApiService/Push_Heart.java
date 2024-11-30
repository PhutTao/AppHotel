package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

public class Push_Heart {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    // Constructor
    public Push_Heart(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
