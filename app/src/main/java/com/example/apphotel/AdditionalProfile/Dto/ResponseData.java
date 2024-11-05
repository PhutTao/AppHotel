package com.example.apphotel.AdditionalProfile.Dto;


import com.google.gson.annotations.SerializedName;

public class ResponseData {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Object data;
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
