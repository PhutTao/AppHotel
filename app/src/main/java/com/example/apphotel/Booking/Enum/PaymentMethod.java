package com.example.apphotel.Booking.Enum;

import com.google.gson.annotations.SerializedName;

public enum PaymentMethod {
    @SerializedName("CreditCard")
    CREDIT_CARD,

    @SerializedName("DebitCard")
    DEBIT_CARD,

    @SerializedName("Paypal")
    PAYPAL,

    @SerializedName("Other")
    OTHER,
    UNKNOWN // Giá trị mặc định
}
