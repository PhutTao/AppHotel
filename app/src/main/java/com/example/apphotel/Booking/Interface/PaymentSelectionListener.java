package com.example.apphotel.Booking.Interface;

import com.example.apphotel.Booking.Item.BookingPaymentMethod;

public interface PaymentSelectionListener {
    void onPaymentSelected(BookingPaymentMethod paymentData);
}
