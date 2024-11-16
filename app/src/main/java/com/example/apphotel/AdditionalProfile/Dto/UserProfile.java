package com.example.apphotel.AdditionalProfile.Dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserProfile {
    @SerializedName("phone")
    private String phone;
    @SerializedName("sex")
    private String sex="male";
    @SerializedName("dateOfBirth")
    private String dateOfBirth;
    @SerializedName("address")
    private String address="";
    @SerializedName("firstname")
    private String firstname="";
    @SerializedName("lastname")
    private String lastname="";
    @SerializedName("email")
    private String email="";
    @SerializedName("avatarName")
    private String avatarName="";



    public UserProfile(String phone, String sex, String dateOfBirth,  String address) {
        this.phone = phone;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }




    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", avatarName='" + avatarName + '\'' +
                '}';
    }
}
