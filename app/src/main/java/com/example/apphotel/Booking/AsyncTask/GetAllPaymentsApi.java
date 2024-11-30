package com.example.apphotel.Booking.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apphotel.Booking.Api.PaymentApiResponse;
import com.example.apphotel.Booking.Api.PaymentApiService;
import com.example.apphotel.Booking.Api.PaymentRetrofitClient;
import com.example.apphotel.Booking.Fragment.BookingPaymentsSelectBottomSheet;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GetAllPaymentsApi extends AsyncTask<Void, Void, List<BookingPaymentMethod>> {
    private static final String TAG = "GetAllPaymentsApi";
    private ApiCallListener listener;

    public interface ApiCallListener {
        void onGetAllPaymentsCompleted(List<BookingPaymentMethod> payments);
        void onGetAllPaymentsFailure(String errorMessage);

        void onBindViewHolder(@NonNull BookingPaymentsSelectBottomSheet.ViewHolder holder, int position);
    }

    public GetAllPaymentsApi(ApiCallListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("ApiCallListener cannot be null");
        }
        this.listener = listener;
    }

    @Override
    protected List<BookingPaymentMethod> doInBackground(Void... voids) {
        try {
            // Khởi tạo API Service
            PaymentApiService apiService = PaymentRetrofitClient.getRetrofitInstance().create(PaymentApiService.class);

            // Gọi API để lấy danh sách phương thức thanh toán
            Call<PaymentApiResponse> call = apiService.getAllPayments();

            // Xử lý phản hồi API
            Response<PaymentApiResponse> response = call.execute();

            Log.d(TAG, "Response Code: " + response.code());
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getData(); // Trả về danh sách phương thức thanh toán
            } else {
                // Log lỗi nếu phản hồi không thành công
                String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                Log.e(TAG, "API Error: " + error);
                return null;
            }
        } catch (IOException e) {
            // Log lỗi khi gặp vấn đề về kết nối
            Log.e(TAG, "IOException occurred while fetching payments: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            Log.e(TAG, "Unexpected exception: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<BookingPaymentMethod> payments) {
        // Kiểm tra listener trước khi sử dụng
        if (listener == null) {
            Log.w(TAG, "Listener is null. Cannot notify the result.");
            return;
        }

        // Gửi kết quả qua listener
        if (payments != null) {
            listener.onGetAllPaymentsCompleted(payments);
        } else {
            listener.onGetAllPaymentsFailure("Failed to fetch payment methods. Please check the API or network connection.");
        }
    }
}
