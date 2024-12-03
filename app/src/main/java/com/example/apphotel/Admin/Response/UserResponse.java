package com.example.apphotel.Admin.Response;


import com.example.apphotel.Admin.Entity.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<User> data;

    public UserResponse(boolean success, String message, List<User> data) {
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

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }
}
