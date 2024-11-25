package com.example.apphotel.Booking.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.apphotel.Booking.AsyncTask.PostBookingApi;
import com.example.apphotel.Booking.AsyncTask.PostPaymentApi;
import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Dto.BookingDto;
import com.example.apphotel.Booking.Dto.PaymentDto;
import com.example.apphotel.Booking.Enum.PaymentMethod;
import com.example.apphotel.Booking.Fragment.AlertDialogFragment;
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

    //    String[] cards = { "Credit card", "Momo", "PayPal", "Other" };
    Map<String, String> cardsItem = new HashMap<>();

    AutoCompleteTextView cardDropdown;
    ArrayAdapter<String> cardAdapter;
    BookingFormDetailData bookingFormDetailData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_add_payment_layout);

        // Initialize Data
        cardsItem.put("Credit card", "Credit_Card");
        cardsItem.put("Momo", "Momo");
        cardsItem.put("PayPal", "PayPal");
        cardsItem.put("Other card", "Other");

        // Handle get intent
        Intent intent = getIntent();
        if (intent.getAction() != null && intent.getAction().equals(Constants.ACTION_CHECKOUT_TO_ADDCARD)) {
            Bundle bookingActivityBundle = intent.getExtras();
            if (bookingActivityBundle != null) {
                bookingFormDetailData = (BookingFormDetailData) bookingActivityBundle.getSerializable("bookingFormData");
            }
        }

        // Define element
        backBtn = findViewById(R.id.checkout_payment_back_button);
        addCardBtn = findViewById(R.id.payment_add_card_button);
        edtCardNumber = findViewById(R.id.payment_card_number);
        edtCardName = findViewById(R.id.payment_card_name);
        cardDropDownTextInputLayout = findViewById(R.id.checkout_card_dropdown_text_input_layout);

        // Set up card dropdown
        String[] cardsName = cardsItem.keySet().toArray(new String[0]);
        cardDropdown = findViewById(R.id.checkout_card_dropdown);
        cardAdapter = new ArrayAdapter<String>(this, R.layout.booking_card_item, cardsName);
        cardDropdown.setAdapter(cardAdapter);

        setUpHandleEventOnCardDropdown();
        setUpHandleClickedBackBtn();
        setUpHandleClickedAddCardBtn();
    }

    private String checkDataBeforeAddCart() {
        if (edtCardNumber.getText().toString().equals("")) {
            return "Please enter your card number";
        }

        if (edtCardName.getText().toString().equals("")) {
            return "Please enter your card name";
        }

        if (cardDropdown.getText().toString().equals("")) {
            return "Please select your payment method";
        }

        return Constants.STATE_OK;
    }

    private void setUpHandleClickedAddCardBtn() {
        addCardBtn.setOnClickListener(v -> {
            SharedPreferences preferences = (BookingAddCardActivity.this).getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String authToken = preferences.getString("jwtKey", null);

            if (checkDataBeforeAddCart() == Constants.STATE_OK) {
                String cardType = cardsItem.get(cardDropdown.getText().toString());
                String cardName = edtCardName.getText().toString();
                String cardNumber = edtCardNumber.getText().toString();

                PaymentDto paymentDto = new PaymentDto(cardType, cardName, cardNumber);

                boolean postPaymentSuccessful;
                try {
                    postPaymentSuccessful = new PostPaymentApi(BookingAddCardActivity.this, authToken, paymentDto).execute().get();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (postPaymentSuccessful) {
                    Intent intent = new Intent(this, BookingCheckoutActivity.class);
                    intent.setAction(Constants.ACTION_ADDCARD_TO_CHECKOUT);

                    bookingFormDetailData.setBookingPaymentMethod(new BookingPaymentMethod(paymentDto.getCardName(), paymentDto.getCardNumber(), PaymentMethod.valueOf(paymentDto.getPaymentMethod())));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bookingFormData", (Serializable) bookingFormDetailData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Post payment successfully", Toast.LENGTH_SHORT).show();
                }
            } else {
                AlertDialogFragment.showAlertDialog(this,"Lack of information", checkDataBeforeAddCart());
            }
        });
    }

    private void setUpHandleEventOnCardDropdown() {
        cardDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });
        cardDropdown.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // When the AutoCompleteTextView gains focus (dropdown clicked), hide the hint
                    cardDropDownTextInputLayout.setHint("");
                } else {
                    // When the AutoCompleteTextView loses focus, set the hint back
                    cardDropDownTextInputLayout.setHint("Select type of card");
                }
            }
        });

        cardDropdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                cardDropDownTextInputLayout.setHint("");
                return false;
            }
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
                // Hide the hint if the AutoCompleteTextView has a value
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

            Bundle bundle = new Bundle();
            bundle.putSerializable("bookingFormData", (Serializable) bookingFormDetailData);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }
}