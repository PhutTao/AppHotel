package com.example.apphotel.Booking.AsyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.apphotel.Booking.Api.PaymentApiResponse;
import com.example.apphotel.Booking.Api.PaymentApiService;
import com.example.apphotel.Booking.Api.PaymentRetrofitClient;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GetAllPaymentsApi extends AsyncTask<Void, Void, List<BookingPaymentMethod>> {
    private Context context;
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onGetAllPaymentsCompleted(List<BookingPaymentMethod> payments);
        void onGetAllPaymentsFailure(String errorMessage);
    }

    public GetAllPaymentsApi(Context context, ApiCallListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<BookingPaymentMethod> doInBackground(Void... voids) {
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String authToken = preferences.getString("jwtKey", null);
        if (authToken != null) {
            try {
                PaymentApiService apiService = PaymentRetrofitClient.getRetrofitInstance().create(PaymentApiService.class);
                Call<PaymentApiResponse> call = apiService.getAllPayments("Bearer " + authToken);

                Response<PaymentApiResponse> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    return response.body().getData();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BookingPaymentMethod> payments) {
        super.onPostExecute(payments);
        if (payments != null) {
            listener.onGetAllPaymentsCompleted(payments);
        } else {
            listener.onGetAllPaymentsFailure("API call failed");
        }
    }
}
