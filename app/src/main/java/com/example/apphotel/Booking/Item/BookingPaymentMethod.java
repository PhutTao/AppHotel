package com.example.apphotel.Booking.Item;

import com.example.apphotel.Booking.Enum.PaymentMethod;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BookingPaymentMethod implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("userName")
    private String userName;

    @SerializedName("cardName")
    private String cardName;

    @SerializedName("cardNumber")
    private String cardNumber;

    @SerializedName("numberCVV")
    private String numberCVV;

    @SerializedName("member_since")
    private String memberSince;

    @SerializedName("paymentMethod")
    private PaymentMethod paymentMethod;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Constructor
    public BookingPaymentMethod(String cardName, String cardNumber, PaymentMethod paymentMethod) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
        this.paymentMethod = paymentMethod;
    }
}
