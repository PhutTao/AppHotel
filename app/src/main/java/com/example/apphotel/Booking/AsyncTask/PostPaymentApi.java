package com.example.apphotel.Booking.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.apphotel.Booking.Api.PaymentApiService;
import com.example.apphotel.Booking.Api.PaymentRetrofitClient;
import com.example.apphotel.Booking.Dto.PaymentDto;

import retrofit2.Call;
import retrofit2.Response;

public class PostPaymentApi extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "PostPaymentApi";

    private Context context;
    private PaymentDto paymentDto;

    public PostPaymentApi(Context context, PaymentDto paymentDto) {
        this.context = context;
        this.paymentDto = paymentDto;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        PaymentApiService paymentService = PaymentRetrofitClient.getRetrofitInstance().create(PaymentApiService.class);
        Call<Void> call = paymentService.postPayment(paymentDto);

        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                Log.d(TAG, "Post payment successful");
                return true;
            } else {
                Log.e(TAG, "Post payment failed: " + response.code());
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Post payment exception: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Payment added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to add payment. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
