package com.example.apphotel.Booking.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.apphotel.Booking.AsyncTask.PostBookingApi;
import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Dto.BookingDto;
import com.example.apphotel.Booking.Fragment.AlertDialogFragment;
import com.example.apphotel.Booking.Fragment.BookingPaymentsSelectBottomSheet;
import com.example.apphotel.Booking.Fragment.YesNoDialogFragment;
import com.example.apphotel.Booking.Interface.PaymentSelectionListener;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;
import com.example.apphotel.Booking.Item.BookingRoomType;
import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.R;
import com.example.apphotel.Searching.AsyncTask.DetailHotelApiCallAsyncTask;
import com.example.apphotel.Searching.Domain.Hotel;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BookingCheckoutActivity extends AppCompatActivity implements
        DetailHotelApiCallAsyncTask.ApiCallListener,
        PaymentSelectionListener {
    private FrameLayout backBtn;
    private ImageView hotelImage;
    private TextView hotelName, hotelAddress, hotelPrice, hotelRate, hotelReviewNumber;
    private TextView dateInfor, guestInfor, phoneInfor, roomTypeInfor, selectPaymentBtn, hotelBillPrice, totalBillPrice;
    private AppCompatButton confirmBtn;
    private BookingPaymentsSelectBottomSheet paymentsSelectBottomSheet;

    private BookingFormDetailData bookingFormDetailData = new BookingFormDetailData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_layout);

        // Define hotel element
        hotelImage = findViewById(R.id.checkout_hotel_image);
        hotelName = findViewById(R.id.checkout_hotel_name);
        hotelAddress = findViewById(R.id.checkout_hotel_address);
        hotelPrice = findViewById(R.id.checkout_hotel_price);
        hotelRate = findViewById(R.id.checkout_star_rate);
        hotelReviewNumber = findViewById(R.id.checkout_hotel_reviews_number);

        // Define detail checkout form
        dateInfor = findViewById(R.id.checkout_booking_information_dates);
        guestInfor = findViewById(R.id.checkout_booking_information_guest);
        phoneInfor = findViewById(R.id.checkout_booking_information_phone_number);
        roomTypeInfor = findViewById(R.id.checkout_booking_information_room_type);
        hotelBillPrice = findViewById(R.id.checkout_booking_detail_price);
        totalBillPrice = findViewById(R.id.checkout_booking_detail_total_price);
        confirmBtn = findViewById(R.id.checkout_confirm_button);
        selectPaymentBtn = findViewById(R.id.booking_payment_bottom_sheet_open_button);

        // Get data which user had selected before
        Intent bookingActivityIntent = getIntent();

        if (bookingActivityIntent.getAction() != null && bookingActivityIntent.getAction().equals(Constants.ACTION_BOOKING_TO_CHECKOUT)) {
            Bundle bookingActivityBundle = bookingActivityIntent.getExtras();
            if (bookingActivityBundle != null) {
                bookingFormDetailData = (BookingFormDetailData) bookingActivityBundle.getSerializable("bookingFormData");
                Integer bookingHotelId = bookingFormDetailData.getHotelId();

                getDetailHotel(bookingHotelId);
                updateCheckoutView(bookingFormDetailData);
            }
        }

        if (bookingActivityIntent.getAction() != null && bookingActivityIntent.getAction().equals(Constants.ACTION_ADDCARD_TO_CHECKOUT)) {
            Bundle bookingActivityBundle = bookingActivityIntent.getExtras();
            if (bookingActivityBundle != null) {
                bookingFormDetailData = (BookingFormDetailData) bookingActivityBundle.getSerializable("bookingFormData");
                Integer bookingHotelId = bookingFormDetailData.getHotelId();

                getDetailHotel(bookingHotelId);
                updateCheckoutView(bookingFormDetailData);
            }
        }

        setUpNavigateToBookingActivity();
        setUpOpenPaymentsSelectBottomSheet();
        setUpConfirmButtonClick();
    }

    private void setUpConfirmButtonClick() {
        confirmBtn.setOnClickListener(v -> {
            SharedPreferences preferences = (BookingCheckoutActivity.this).getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String authToken = preferences.getString("jwtKey", null);

            if (bookingFormDetailData.getBookingPaymentMethod() == null) {
                AlertDialogFragment.showAlertDialog(this,"Alert", "Please select your payment before booking");
            } else {
                int hotelId = bookingFormDetailData.getHotelId();

                BookingDto bookingDto = new BookingDto();
                // Set hotelId
                bookingDto.setHotelId(hotelId);
                // Set start date and end date
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                String startDate = dateFormat.format(bookingFormDetailData.getStartDate()); // Replace this with your actual start date
                String endDate = dateFormat.format(bookingFormDetailData.getEndDate());   // Replace this with your actual end date
                bookingDto.setStartDate(startDate);
                bookingDto.setEndDate(endDate);
                // Set other properties
                bookingDto.setAdultsQuantity(bookingFormDetailData.getSelectedAdultValue());
                bookingDto.setChildQuantity(bookingFormDetailData.getSelectedChildValue());
                bookingDto.setPhoneNumber(bookingFormDetailData.getPhoneNumber());
                bookingDto.setHotelRate(5.0);
                bookingDto.setReviewQuantity(0);
                bookingDto.setPaymentMethod(bookingFormDetailData.getBookingPaymentMethod().getPaymentMethod().toString());
                // Set room types
                List<String> roomTypes = new ArrayList<>();
                for (BookingRoomType roomType : bookingFormDetailData.getRoomTypeList()) {
                    roomTypes.add(roomType.getRoomType().toString());
                }
                bookingDto.setRoomTypes(roomTypes);

                new PostBookingApi(BookingCheckoutActivity.this, authToken, hotelId, bookingDto).execute();
            }
        });
    }

    public static void showToastWithJson(Context context, BookingDto bookingDto) {
        if (context == null || bookingDto == null) {
            return;
        }

        // Convert BookingDto to JSON
        Gson gson = new Gson();
        String jsonBookingDto = gson.toJson(bookingDto);

        String valueDisplay = bookingDto.getPaymentMethod();

        // Show JSON as a toast
        Toast.makeText(context, valueDisplay, Toast.LENGTH_LONG).show();
    }

    private static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return dateFormat.format(date);
    }

    private void getDetailHotel(int hotelId) {
        new DetailHotelApiCallAsyncTask(this, this).execute(hotelId);
    }

    private void setUpOpenPaymentsSelectBottomSheet() {
        selectPaymentBtn.setOnClickListener(v -> {
            paymentsSelectBottomSheet = new BookingPaymentsSelectBottomSheet(bookingFormDetailData);
            paymentsSelectBottomSheet.show(getSupportFragmentManager(), paymentsSelectBottomSheet.getTag());
        });
    }

    private void setUpNavigateToBookingActivity() {
        backBtn = findViewById(R.id.checkout_back_button);
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.setAction(Constants.ACTION_CHECKOUT_TO_BOOKING);

            Bundle bundle = new Bundle();
            bundle.putSerializable("bookingFormData", (Serializable) bookingFormDetailData);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }

    private void setUpClickConfirmCheckout() {
        confirmBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomescreenActivity.class);
            startActivity(intent);
        });
    }

    private void updateCheckoutView(BookingFormDetailData data) {
        String dateFormatted = formatDateRange(data.getStartDate(), data.getEndDate());
        String guestRoomQuantity = (data.getSelectedAdultValue() + data.getSelectedChildValue()) + " guests " + "- " + data.getSelectedRoomValue() + " rooms";
        String roomTypeQuantity = data.getRoomTypeList().size() + " room types";
        String phoneNumber = data.getPhoneNumber();

        dateInfor.setText(dateFormatted);
        guestInfor.setText(guestRoomQuantity);
        roomTypeInfor.setText(roomTypeQuantity);
        phoneInfor.setText(phoneNumber);
    }

    private String formatDateRange(Date startDate, Date endDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedStart = dateFormat.format(startDate);
        String formattedEnd = dateFormat.format(endDate);

        return formattedStart + " - " + formattedEnd;
    }

    @Override
    public void onApiCallSuccess(Hotel hotel) {
        Glide.with(this).load(hotel.getImageDetails().get(0).getImg()).into(hotelImage);
        hotelName.setText(hotel.getName());
        hotelAddress.setText(hotel.getAddress());
        hotelReviewNumber.setText("(" + hotel.getReviewQuantity() + " reviews)");

        // Create a DecimalFormat object
        // Format the number
        Double hotelPriceValue = hotel.getPrice();
        Double hotelRateValue = hotel.getRate();
        Date hotelDateBookedStart = bookingFormDetailData.getStartDate();
        Date hotelDateBookedEnd = bookingFormDetailData.getEndDate();

        Long totalDayBooked = getDateDiff(hotelDateBookedStart, hotelDateBookedEnd, TimeUnit.DAYS);
        Double hotelTotalPriceBooked = totalDayBooked * hotelPriceValue * bookingFormDetailData.getSelectedRoomValue();
        Double hotelTotalBillPriceBooked = hotelTotalPriceBooked + 2.5;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String hotelPriceFormatted = "$" + decimalFormat.format(hotelPriceValue);
        String hotelRateFormatted = decimalFormat.format(hotelRateValue);
        String hotelTotalPriceBookedFormatted = "$" + decimalFormat.format(hotelTotalPriceBooked);
        String hotelTotalBillPriceBookedFormatted = "$" + decimalFormat.format(hotelTotalBillPriceBooked);

        hotelPrice.setText(hotelPriceFormatted);
        hotelRate.setText(hotelRateFormatted);

        hotelBillPrice.setText(hotelTotalPriceBookedFormatted);
        totalBillPrice.setText(hotelTotalBillPriceBookedFormatted);
    }

    private static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMilliseconds = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onApiCallFailure(String errorMessage) {
        Log.e("API Error! Fail to get hotel", errorMessage);
    }

    @Override
    public void onPaymentSelected(BookingPaymentMethod paymentData) {
        bookingFormDetailData.setBookingPaymentMethod(paymentData);
        selectPaymentBtn.setText(paymentData.getCardName());
    }
}
