package com.example.apphotel.Booking.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.apphotel.Booking.Api.BookingApiService;
import com.example.apphotel.Booking.Api.PaymentApiService;
import com.example.apphotel.Booking.Api.PaymentRetrofitClient;
import com.example.apphotel.Booking.Dto.BookingDto;
import com.example.apphotel.Booking.Dto.PaymentDto;
import com.example.apphotel.Homescreen.HomescreenActivity;

import retrofit2.Call;
import retrofit2.Response;

public class PostPaymentApi extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private String authToken;
    private PaymentDto paymentDto;
    private ProgressDialog progressDialog;

    public PostPaymentApi(Context context, String authToken, PaymentDto paymentDto) {
        this.context = context;
        this.authToken = authToken;
        this.paymentDto = paymentDto;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Adding your payment...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        PaymentApiService paymentService = PaymentRetrofitClient.getRetrofitInstance().create(PaymentApiService.class);
        Call<Void> call = paymentService.postPayment("Bearer " + authToken, paymentDto);

        try {
            Response<Void> response = call.execute();

            if (response.isSuccessful()) {
                Log.e("API RESPONSE SUCCESS - POST PAYMENT", "Successfully");
                return true;
            } else {
                Log.e("API RESPONSE ERROR - POST PAYMENT", "Unsuccessful: " + response.code());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("API CALL POST PAYMENT FAILURE", "Network failure");
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // Start another activity upon success
            progressDialog.dismiss();
            Log.e("API CALL POST PAYMENT", "POST PAYMENT SUCCESSFULLY");
        } else {
            progressDialog.dismiss();
            Log.e("API CALL POST PAYMENT", "POST PAYMENT FAILED");
            // Handle failure or show a message
            // You may add a callback or interface to communicate with the calling activity/fragment
        }
    }
}
