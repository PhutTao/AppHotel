package com.example.apphotel.Booking.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.apphotel.Booking.Api.PaymentApiService;
import com.example.apphotel.Booking.Api.PaymentRetrofitClient;

import retrofit2.Call;
import retrofit2.Response;

public class DeletePaymentApi extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "DeletePaymentApi";

    private Context context;
    private int paymentId;

    public DeletePaymentApi(Context context, int paymentId) {
        this.context = context;
        this.paymentId = paymentId;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        PaymentApiService paymentApiService = PaymentRetrofitClient.getRetrofitInstance().create(PaymentApiService.class);
        Call<Void> call = paymentApiService.deletePayment(String.valueOf(paymentId));

        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful()) {
                Log.d(TAG, "Delete payment successful");
                return true;
            } else {
                Log.e(TAG, "Delete payment failed: " + response.code());
                return false;
            }
        } catch (Exception e) {
            Log.e(TAG, "Delete payment exception: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Toast.makeText(context, "Payment deleted successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to delete payment.", Toast.LENGTH_SHORT).show();
        }
    }
}
