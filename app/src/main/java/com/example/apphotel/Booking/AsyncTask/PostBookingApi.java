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

public class PostBookingApi extends AsyncTask<Void, Void, String> {
    private Context context;
    private int hotelId;
    private BookingDto bookingDto;
    private ProgressDialog progressDialog;

    public PostBookingApi(Context context, BookingDto bookingDto) {
        this.context = context;
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
    protected String doInBackground(Void... voids) {
        BookingApiService bookingService = PaymentRetrofitClient.getRetrofitInstance().create(BookingApiService.class);
        Call<Void> call = bookingService.postBookingWithoutToken(bookingDto);

        try {
            Response<Void> response = call.execute();
            System.out.println(response.body());
            if (response.isSuccessful()) {
                Log.d("API_RESPONSE", "Booking successful");
                return "success";
            } else {
                // Lấy thông báo lỗi chi tiết từ response
                String errorMessage = "Error: " + response.code() + " - " + response.message();
                Log.e("API_RESPONSE_ERROR", errorMessage);
                return errorMessage;
            }
        } catch (Exception e) {
            Log.e("API_CALL_FAILURE", "Network failure: " + e.getMessage());
            e.printStackTrace();
            return "Network failure: " + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.dismiss();

        if ("success".equals(result)) {
            // Chuyển hướng sang màn hình chính khi đặt phòng thành công
            Intent intent = new Intent(context, HomescreenActivity.class);
            intent.putExtra("navigateTo", "mybookingfragment");
            context.startActivity(intent);
            Toast.makeText(context, "Booking successful!", Toast.LENGTH_SHORT).show();
        } else {
            // Hiển thị thông báo lỗi
            Toast.makeText(context, "Booking failed: " + result, Toast.LENGTH_LONG).show();
        }
    }
}
