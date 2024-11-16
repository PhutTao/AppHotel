package com.example.apphotel.Api;

import com.example.apphotel.Model.Hotel;

import java.util.List;

public class ApiResponse {
    private boolean success;
    private List<Hotel> data;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public List<Hotel> getData() { return data; }
    public void setData(List<Hotel> data) { this.data = data; }
}
