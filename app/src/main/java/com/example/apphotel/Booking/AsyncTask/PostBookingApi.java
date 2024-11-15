package com.example.apphotel.Booking.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.apphotel.Booking.Api.BookingApiService;
import com.example.apphotel.Booking.Api.PaymentRetrofitClient;
import com.example.apphotel.Booking.Dto.BookingDto;
import com.example.apphotel.Homescreen.HomescreenActivity;

import retrofit2.Call;
import retrofit2.Response;

public class PostBookingApi extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private String authToken;
    private int hotelId;
    private BookingDto bookingDto;
    private ProgressDialog progressDialog;

    public PostBookingApi(Context context, String authToken, int hotelId, BookingDto bookingDto) {
        this.context = context;
        this.authToken = authToken;
        this.hotelId = hotelId;
        this.bookingDto = bookingDto;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Booking your hotel...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        BookingApiService bookingService = PaymentRetrofitClient.getRetrofitInstance().create(BookingApiService.class);
        Call<Void> call = bookingService.postBooking("Bearer " + authToken, hotelId, bookingDto);

        try {
            Response<Void> response = call.execute();

            if (response.isSuccessful()) {
                Log.e("API RESPONSE SUCCESS - POST", "Successfully");
                return true;
            } else {
                Log.e("API RESPONSE ERROR - POST", "Unsuccessful: " + response.code());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("API CALL FAILURE", "Network failure");
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            // Start another activity upon success
            progressDialog.dismiss();
            Intent intent = new Intent(context, HomescreenActivity.class);
//            context.startActivity(intent);
            intent.putExtra("navigateTo", "mybookingfragment");
            context.startActivity(intent);

        } else {
            progressDialog.dismiss();
            Toast.makeText(context, "Booking hotel failed!", Toast.LENGTH_SHORT).show();
            // Handle failure or show a message
            // You may add a callback or interface to communicate with the calling activity/fragment
        }
    }
}
