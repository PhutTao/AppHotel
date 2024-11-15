package com.example.apphotel.Booking.Item;

import com.example.apphotel.Booking.Enum.PaymentMethod;

import java.io.Serializable;

public class BookingPaymentMethod implements Serializable {
    private int id;
    private String userName;
    private String cardName;
    private String carNumber;
    private PaymentMethod paymentMethod;
    private String numberCVV;
    private String memberSince;

    public BookingPaymentMethod(String cardName, String cardNumber, PaymentMethod paymentMethod) {
        this.cardName = cardName;
        this.carNumber = cardNumber;
        this.paymentMethod = paymentMethod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return carNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.carNumber = cardNumber;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
