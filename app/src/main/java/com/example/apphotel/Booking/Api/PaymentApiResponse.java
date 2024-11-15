package com.example.apphotel.Booking.Api;

import com.example.apphotel.Booking.Item.BookingPaymentMethod;

import java.util.List;

public class PaymentApiResponse {
    private String message;
    private List<BookingPaymentMethod> data;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BookingPaymentMethod> getData() {
        return data;
    }

    public void setData(List<BookingPaymentMethod> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PaymentApiResponse(String message, List<BookingPaymentMethod> data, String status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }
}
