package com.example.apphotel.Register.dto;


import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")

    private String email;
    @SerializedName("password")


    private String password;
    @SerializedName("firstname")
    private String firstname;

    public RegisterRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RegisterRequest(String email, String password, String firstname) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
