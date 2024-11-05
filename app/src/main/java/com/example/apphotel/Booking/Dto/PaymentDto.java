package com.example.apphotel.Booking.Dto;

import com.google.gson.annotations.SerializedName;

public class PaymentDto {
    private String username;
    private String cardName;
    @SerializedName("carNumber")
    private String cardNumber;
    private String numberCVV;
    private String memberSince;
    private String paymentMethod;

    public  PaymentDto(String paymentMethod, String cardName, String cardNumber) {
        this.paymentMethod = paymentMethod;
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.username = "";
        this.memberSince = "";
        this.numberCVV = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNumberCVV() {
        return numberCVV;
    }

    public void setNumberCVV(String numberCVV) {
        this.numberCVV = numberCVV;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
