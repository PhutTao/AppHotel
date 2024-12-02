package com.example.apphotel.Booking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Fragment.AlertDialogFragment;
import com.example.apphotel.Booking.Fragment.BookingGuestsSelectBottomSheet;
import com.example.apphotel.Booking.Fragment.BookingRoomsSelectBottomSheet;
import com.example.apphotel.Booking.Interface.OnSaveClickListener;
import com.example.apphotel.Booking.Item.BookingRoomType;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.util.Pair;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements OnSaveClickListener {
    private FrameLayout backBtn;
    private TextView guestsSelect, roomsSelect, datesSelect;
    private EditText phoneNumberSelect;
    private AppCompatButton continueBtn;
    private CheckBox ckbPolicy1, ckbPolicy2;
    private MaterialDatePicker<Pair<Long, Long>> datePicker;

    private int bookingHotelId;
    private BookingFormDetailData bookingFormDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_layout);

        // Initialize Form Detail Data
        bookingFormDetailData = new BookingFormDetailData();

        // Initialize UI elements
        guestsSelect = findViewById(R.id.guests_number_select);
        roomsSelect = findViewById(R.id.room_type_select);
        datesSelect = findViewById(R.id.dates_select);
        phoneNumberSelect = findViewById(R.id.booking_phone_number);
        continueBtn = findViewById(R.id.booking_continue_button);
        backBtn = findViewById(R.id.booking_back_button);
        ckbPolicy1 = findViewById(R.id.booking_checkbox_policy_1);
        ckbPolicy2 = findViewById(R.id.booking_checkbox_policy_2);

        // Handle Intent data
        handleIntentData();

        // Setup interactions
        setupGuestsSelect(bookingFormDetailData);
        setupRoomsSelect(bookingFormDetailData);
        setupDateSelect();
        setUpNavigateToCheckout();
        setUpNavigateBackToDetail();
    }

    private void handleIntentData() {
        Intent intent = getIntent();
        if (intent.getAction() != null) {
            if (intent.getAction().equals(Constants.ACTION_DETAIL_TO_BOOKING)) {
                bookingHotelId = intent.getIntExtra("hotelId", 0);
            } else if (intent.getAction().equals(Constants.ACTION_CHECKOUT_TO_BOOKING)) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    bookingFormDetailData = (BookingFormDetailData) bundle.getSerializable("bookingFormData");
                    if (bookingFormDetailData != null) {
                        bookingHotelId = bookingFormDetailData.getHotelId();
                        updateBookingFormView(bookingFormDetailData);
                    }
                }
            }
        }
    }



    private void updateBookingFormView(BookingFormDetailData data) {
        if (data != null) {
            String dateFormatted = formatDateRange(data.getStartDate(), data.getEndDate());
            String guestRoomQuantity = (data.getSelectedAdultValue() + data.getSelectedChildValue()) + " guests - " + data.getSelectedRoomValue() + " rooms";
            String roomTypeQuantity = data.getRoomTypeList().size() + " room types";
            String phoneNumber = data.getPhoneNumber();

            datesSelect.setText(dateFormatted);
            guestsSelect.setText(guestRoomQuantity);
            roomsSelect.setText(roomTypeQuantity);
            phoneNumberSelect.setText(phoneNumber);
        }
    }

    private void setUpNavigateBackToDetail() {
        backBtn.setOnClickListener(v -> finish());
    }

    private void setUpNavigateToCheckout() {
        continueBtn.setOnClickListener(v -> {
            bookingFormDetailData.setPhoneNumber(phoneNumberSelect.getText().toString().trim());
            bookingFormDetailData.setHotelId(bookingHotelId);

            String validationMessage = checkDataBeforeNavigateToCheckout();
            if (validationMessage.equals(Constants.STATE_OK)) {
                Intent intent = new Intent(this, BookingCheckoutActivity.class);
                intent.setAction(Constants.ACTION_BOOKING_TO_CHECKOUT);

                Bundle bundle = new Bundle();
                bundle.putSerializable("bookingFormData", (Serializable) bookingFormDetailData);
                intent.putExtras(bundle);

                startActivity(intent);
            } else {
                AlertDialogFragment.showAlertDialog(this, "Lack of information", validationMessage);
            }
        });
    }

    private String checkDataBeforeNavigateToCheckout() {
        if (bookingFormDetailData.getStartDate() == null) {
            return "Please select a start date";
        }
        if (bookingFormDetailData.getEndDate() == null) {
            return "Please select an end date";
        }
        if (bookingFormDetailData.getSelectedChildValue() == 0 && bookingFormDetailData.getSelectedAdultValue() == 0) {
            return "Please choose the number of guests";
        }
        if (bookingFormDetailData.getSelectedRoomValue() == 0) {
            return "Please choose the number of rooms";
        }
        if (bookingFormDetailData.getRoomTypeList().isEmpty()) {
            return "Please select a room type";
        }
        if (bookingFormDetailData.getPhoneNumber().isEmpty()) {
            return "Please provide your phone number";
        }
        if (!ckbPolicy1.isChecked() || !ckbPolicy2.isChecked()) {
            return "Please agree with our policies";
        }
        return Constants.STATE_OK;
    }

    private void setupGuestsSelect(BookingFormDetailData bookingFormDetailData) {
        guestsSelect.setOnClickListener(v -> showGuestsSelectBottomSheet(bookingFormDetailData));
    }

    private void showGuestsSelectBottomSheet(BookingFormDetailData bookingFormDetailData) {
        BookingGuestsSelectBottomSheet guestsSelectBottomSheet = new BookingGuestsSelectBottomSheet(bookingFormDetailData);
        guestsSelectBottomSheet.show(getSupportFragmentManager(), guestsSelectBottomSheet.getTag());
    }

    private void setupRoomsSelect(BookingFormDetailData bookingFormDetailData) {
        roomsSelect.setOnClickListener(v -> showRoomsSelectBottomSheet(bookingFormDetailData));
    }

    private void showRoomsSelectBottomSheet(BookingFormDetailData bookingFormDetailData) {
        BookingRoomsSelectBottomSheet bookingRoomsSelectBottomSheet = new BookingRoomsSelectBottomSheet(bookingFormDetailData);
        bookingRoomsSelectBottomSheet.show(getSupportFragmentManager(), bookingRoomsSelectBottomSheet.getTag());
    }

    private void setupDateSelect() {
        datePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Date")
                .setTheme(R.style.ThemeMaterialCalendar)
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Pair<Long, Long> dateRange = datePicker.getSelection();
            Date startDate = new Date(dateRange.first);
            Date endDate = new Date(dateRange.second);
            String formattedDateRange = formatDateRange(startDate, endDate);

            bookingFormDetailData.setStartDate(startDate);
            bookingFormDetailData.setEndDate(endDate);
            datesSelect.setText(formattedDateRange);
        });

        datesSelect.setOnClickListener(v -> datePicker.show(getSupportFragmentManager(), "datePicker_tag"));
    }

    private String formatDateRange(Date startDate, Date endDate) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(startDate) + " - " + dateFormat.format(endDate);
    }

    @Override
    public void onSaveClick(int totalGuests, int totalRooms) {
        String selectedValue = totalGuests + " guests - " + totalRooms + " rooms";
        guestsSelect.setText(selectedValue);
    }

    @Override
    public void onSelectClick(ArrayList<BookingRoomType> roomTypeList) {
        String displayValue = roomTypeList.size() + " room types";
        bookingFormDetailData.setRoomTypeList(roomTypeList);
        roomsSelect.setText(displayValue);
    }
}
