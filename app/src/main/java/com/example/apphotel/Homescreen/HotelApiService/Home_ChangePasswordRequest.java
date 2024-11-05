package com.example.apphotel.Homescreen.HotelApiService;

public class Home_ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    public Home_ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
