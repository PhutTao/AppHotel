package com.example.apphotel.Admin.Response;


import com.example.apphotel.Admin.Entity.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailUserResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private User data;

    public DetailUserResponse(boolean success, String message, User data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
