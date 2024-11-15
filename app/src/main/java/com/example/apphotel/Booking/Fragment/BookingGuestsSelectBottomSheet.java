package com.example.apphotel.Booking.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Interface.OnSaveClickListener;
import com.example.apphotel.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BookingGuestsSelectBottomSheet extends BottomSheetDialogFragment {
    private FrameLayout increaseRoomBtn, decreaseRoomBtn;
    private FrameLayout increaseAdultBtn, decreaseAdultBtn;
    private FrameLayout increaseChildBtn, decreaseChildBtn;
    private TextView txtRoomNumberVal, txtAdultNumberVal, txtChildNumberVal;
    private AppCompatButton saveSelectBtn;
    private OnSaveClickListener onSaveClickListener;
    private BookingFormDetailData bookingFormData;

    private View contentView;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private int initialHeight = 1200; // Replace with your desired initial height

    public BookingGuestsSelectBottomSheet() {
        // Required empty public constructor
    }

    public BookingGuestsSelectBottomSheet(BookingFormDetailData bookingFormDetailData) {
        // Required empty public constructor
        this.bookingFormData = bookingFormDetailData;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSaveClickListener = (OnSaveClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSaveClickListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create bottom sheet dialog instance
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        contentView = LayoutInflater.from(getContext()).inflate(R.layout.booking_guests_select_bottom_sheet, null);
        bottomSheetDialog.setContentView(contentView);

        // Bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());
        bottomSheetBehavior.setPeekHeight(1200); // Set the initial peek height

        return bottomSheetDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.booking_guests_select_bottom_sheet, container, false);

        increaseRoomBtn = rootView.findViewById(R.id.booking_increase_room_button);
        decreaseRoomBtn = rootView.findViewById(R.id.booking_decrease_room_button);
        increaseAdultBtn = rootView.findViewById(R.id.booking_increase_adult_button);
        decreaseAdultBtn = rootView.findViewById(R.id.booking_decrease_adult_button);
        increaseChildBtn = rootView.findViewById(R.id.booking_increase_child_button);
        decreaseChildBtn = rootView.findViewById(R.id.booking_decrease_child_button);
        txtRoomNumberVal = rootView.findViewById(R.id.booking_room_number_select_value);
        txtAdultNumberVal = rootView.findViewById(R.id.booking_adult_number_select_value);
        txtChildNumberVal = rootView.findViewById(R.id.booking_child_number_select_value);
        saveSelectBtn = rootView.findViewById(R.id.booking_guests_select_save_button);

        // Set up displaying the selected value
        txtRoomNumberVal.setText(String.valueOf(bookingFormData.getSelectedRoomValue()));
        txtAdultNumberVal.setText(String.valueOf(bookingFormData.getSelectedAdultValue()));
        txtChildNumberVal.setText(String.valueOf(bookingFormData.getSelectedChildValue()));

        // Set up increasing, decreasing guest, room number
        setUpRoomNumberAdjust();
        setUpAdultNumberAdjust();
        setUpChildNumberAdjust();
        setUpSaveSelect();

        return rootView;
    }

    public void setUpBookingGuestsBottomSheetView() {

    }

    public void setUpRoomNumberAdjust() {
        increaseRoomBtn.setOnClickListener(v -> {
            int selectedRoomVal = bookingFormData.getSelectedRoomValue();
            int newSelectedRoomVal = selectedRoomVal + 1;

            txtRoomNumberVal.setText(String.valueOf(newSelectedRoomVal));
            bookingFormData.setSelectedRoomValue(newSelectedRoomVal);
        });

        decreaseRoomBtn.setOnClickListener(v -> {
            int selectedRoomVal = bookingFormData.getSelectedRoomValue();

            if (selectedRoomVal > 0) {
                int newSelectedRoomVal = selectedRoomVal - 1;

                txtRoomNumberVal.setText(String.valueOf(newSelectedRoomVal));
                bookingFormData.setSelectedRoomValue(newSelectedRoomVal);
            }
        });
    }

    public void setUpAdultNumberAdjust() {
        increaseAdultBtn.setOnClickListener(v -> {
            int selectedAdultVal = bookingFormData.getSelectedAdultValue();
            int newSelectedAdultVal = selectedAdultVal + 1;

            txtAdultNumberVal.setText(String.valueOf(newSelectedAdultVal));
            bookingFormData.setSelectedAdultValue(newSelectedAdultVal);
        });

        decreaseAdultBtn.setOnClickListener(v -> {
            int selectedAdultVal = bookingFormData.getSelectedAdultValue();

            if (selectedAdultVal > 0) {
                int newSelectedAdultVal = selectedAdultVal - 1;

                txtAdultNumberVal.setText(String.valueOf(newSelectedAdultVal));
                bookingFormData.setSelectedAdultValue(newSelectedAdultVal);
            }
        });
    }

    public void setUpChildNumberAdjust() {
        increaseChildBtn.setOnClickListener(v -> {
            int selectedChildVal = bookingFormData.getSelectedChildValue();
            int newSelectedChildVal = selectedChildVal + 1;

            txtChildNumberVal.setText(String.valueOf(newSelectedChildVal));
            bookingFormData.setSelectedChildValue(newSelectedChildVal);
        });

        decreaseChildBtn.setOnClickListener(v -> {
            int selectedChildVal = bookingFormData.getSelectedChildValue();

            if (selectedChildVal > 0) {
                int newSelectedChildVal = selectedChildVal - 1;

                txtChildNumberVal.setText(String.valueOf(newSelectedChildVal));
                bookingFormData.setSelectedChildValue(newSelectedChildVal);
            }
        });
    }

    public void setUpSaveSelect() {
        saveSelectBtn.setOnClickListener(v -> {
            int selectedAdultVal = bookingFormData.getSelectedAdultValue();
            int selectedChildVal = bookingFormData.getSelectedChildValue();
            int totalSelectedRoomVal = bookingFormData.getSelectedRoomValue();
            int totalSelectedGuestsNumber = selectedAdultVal + selectedChildVal;

            onSaveClickListener.onSaveClick(totalSelectedGuestsNumber, totalSelectedRoomVal);

            dismiss();
        });
    }
}
