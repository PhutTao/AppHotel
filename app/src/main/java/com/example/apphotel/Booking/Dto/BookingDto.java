package com.example.apphotel.Booking.Dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingDto {
    @SerializedName("startDate")
    private String startDate;
    @SerializedName("endDate")
    private String endDate;
    @SerializedName("adultsQuantity")
    private int adultsQuantity;
    @SerializedName("childQuantity")
    private int childQuantity;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("paymentMethod")
    private String paymentMethod;
    @SerializedName("roomTypes")
    private List<String> roomTypes;
    @SerializedName("hotelRate")
    private Double hotelRate;
    @SerializedName("reviewQuantity")
    private int reviewQuantity;
    @SerializedName("hotelId")
    private int hotelId;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setAdultsQuantity(int adultsQuantity) {
        this.adultsQuantity = adultsQuantity;
    }

    public void setChildQuantity(int childQuantity) {
        this.childQuantity = childQuantity;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setRoomTypes(List<String> roomTypes) {
        this.roomTypes = roomTypes;
    }

    public void setHotelRate(Double hotelRate) {
        this.hotelRate = hotelRate;
    }

    public void setReviewQuantity(int reviewQuantity) {
        this.reviewQuantity = reviewQuantity;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getAdultsQuantity() {
        return adultsQuantity;
    }

    public int getChildQuantity() {
        return childQuantity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public List<String> getRoomTypes() {
        return roomTypes;
    }

    public Double getHotelRate() {
        return hotelRate;
    }

    public int getReviewQuantity() {
        return reviewQuantity;
    }

    public int getHotelId() {
        return hotelId;
    }
}
