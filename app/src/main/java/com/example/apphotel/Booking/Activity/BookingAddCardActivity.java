package com.example.apphotel.Booking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.apphotel.Booking.AsyncTask.PostPaymentApi;
import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Dto.PaymentDto;
import com.example.apphotel.Booking.Enum.PaymentMethod;
import com.example.apphotel.Booking.Item.BookingPaymentMethod;
import com.example.apphotel.R;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class BookingAddCardActivity extends AppCompatActivity {

    private FrameLayout backBtn;
    private EditText edtCardNumber, edtCardName;
    private TextInputLayout cardDropDownTextInputLayout;
    private AppCompatButton addCardBtn;
    private AutoCompleteTextView cardDropdown;

    private Map<String, String> cardsItem = new HashMap<>();
    private BookingFormDetailData bookingFormDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_add_payment_layout);

        initializeData();
        handleIntent();
        defineElements();
        setUpCardDropdown();
        setUpHandleEventOnCardDropdown();
        setUpHandleClickedBackBtn();
        setUpHandleClickedAddCardBtn();
    }

    private void initializeData() {
        // Initialize card types
        cardsItem.put("Credit card", "Credit_Card");
        cardsItem.put("Momo", "Momo");
        cardsItem.put("PayPal", "PayPal");
        cardsItem.put("Other card", "Other");
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if (intent.getAction() != null && intent.getAction().equals(Constants.ACTION_CHECKOUT_TO_ADDCARD)) {
            Bundle bookingActivityBundle = intent.getExtras();
            if (bookingActivityBundle != null) {
                bookingFormDetailData = (BookingFormDetailData) bookingActivityBundle.getSerializable("bookingFormData");
            }
        }
    }

    private void defineElements() {
        backBtn = findViewById(R.id.checkout_payment_back_button);
        addCardBtn = findViewById(R.id.payment_add_card_button);
        edtCardNumber = findViewById(R.id.payment_card_number);
        edtCardName = findViewById(R.id.payment_card_name);
        cardDropDownTextInputLayout = findViewById(R.id.checkout_card_dropdown_text_input_layout);
        cardDropdown = findViewById(R.id.checkout_card_dropdown);
    }

    private void setUpCardDropdown() {
        String[] cardsName = cardsItem.keySet().toArray(new String[0]);
        ArrayAdapter<String> cardAdapter = new ArrayAdapter<>(this, R.layout.booking_card_item, cardsName);
        cardDropdown.setAdapter(cardAdapter);
    }

    private void setUpHandleClickedAddCardBtn() {
        addCardBtn.setOnClickListener(v -> {
            String validationMessage = checkDataBeforeAddCard();
            if (!validationMessage.equals(Constants.STATE_OK)) {
                Toast.makeText(this, validationMessage, Toast.LENGTH_SHORT).show();
                return;
            }

            String cardType = cardsItem.get(cardDropdown.getText().toString());
            String cardName = edtCardName.getText().toString().trim();
            String cardNumber = edtCardNumber.getText().toString().trim();

            PaymentDto paymentDto = new PaymentDto(cardType, cardName, cardNumber);

            boolean postPaymentSuccessful;
            try {
                postPaymentSuccessful = new PostPaymentApi(this, paymentDto).execute().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                postPaymentSuccessful = false;
            }

            if (postPaymentSuccessful) {
                Intent intent = new Intent(this, BookingCheckoutActivity.class);
                intent.setAction(Constants.ACTION_ADDCARD_TO_CHECKOUT);

                if (bookingFormDetailData != null) {
                    bookingFormDetailData.setBookingPaymentMethod(
                            new BookingPaymentMethod(paymentDto.getCardName(), paymentDto.getCardNumber(), PaymentMethod.valueOf(paymentDto.getPaymentMethod()))
                    );
                    intent.putExtra("bookingFormData", bookingFormDetailData);
                }

                startActivity(intent);
            } else {
                Toast.makeText(this, "Failed to add payment. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String checkDataBeforeAddCard() {
        if (edtCardNumber.getText().toString().trim().isEmpty()) {
            return "Please enter your card number.";
        }

        if (edtCardName.getText().toString().trim().isEmpty()) {
            return "Please enter your card name.";
        }

        if (cardDropdown.getText().toString().trim().isEmpty()) {
            return "Please select your payment method.";
        }

        return Constants.STATE_OK;
    }

    private void setUpHandleEventOnCardDropdown() {
        cardDropdown.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();
            cardDropDownTextInputLayout.setHint("");
        });

        cardDropdown.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                cardDropDownTextInputLayout.setHint("");
            } else {
                cardDropDownTextInputLayout.setHint("Select type of card");
            }
        });

        cardDropdown.setOnTouchListener((v, event) -> {
            cardDropDownTextInputLayout.setHint("");
            return false;
        });

        cardDropdown.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
                    cardDropDownTextInputLayout.setHint("");
                }
            }
        });
    }

    private void setUpHandleClickedBackBtn() {
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, BookingCheckoutActivity.class);
            intent.setAction(Constants.ACTION_ADDCARD_TO_CHECKOUT);

            if (bookingFormDetailData != null) {
                intent.putExtra("bookingFormData", bookingFormDetailData);
            }

            startActivity(intent);
        });
    }
}
