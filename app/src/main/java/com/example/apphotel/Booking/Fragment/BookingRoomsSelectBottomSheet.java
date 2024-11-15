package com.example.apphotel.Booking.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apphotel.Booking.Activity.BookingActivity;
import com.example.apphotel.Booking.Adapter.BookingRoomTypeAdapter;
import com.example.apphotel.Booking.Data.BookingFormDetailData;
import com.example.apphotel.Booking.Enum.RoomType;
import com.example.apphotel.Booking.Interface.OnSaveClickListener;
import com.example.apphotel.Booking.Item.BookingRoomType;
import com.example.apphotel.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class BookingRoomsSelectBottomSheet extends BottomSheetDialogFragment {

    private View contentView;
    private AppCompatButton selectRoomTypeBtn;

    private RecyclerView recyclerView;
    private ArrayList<BookingRoomType> roomTypeList;

    private BottomSheetBehavior<View> bottomSheetBehavior;
    private int initialHeight = 1200; // Replace with your desired initial height

    private OnSaveClickListener onSaveClickListener;
    private BookingFormDetailData bookingFormData;

    public BookingRoomsSelectBottomSheet() {
        // Required empty public constructor
    }

    public BookingRoomsSelectBottomSheet(BookingFormDetailData bookingFormDetailData) {
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
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);

        contentView = LayoutInflater.from(getContext()).inflate(R.layout.booking_rooms_select_bottom_sheet, null);
        bottomSheetDialog.setContentView(contentView);

        // Bottom sheet behavior
        bottomSheetBehavior = BottomSheetBehavior.from((View) contentView.getParent());
        bottomSheetBehavior.setPeekHeight(1400); // Set the initial peek height

        // Create Room Type RecyclerView

        return bottomSheetDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.roomTypeList = new ArrayList<>();
        roomTypeList.add(new BookingRoomType(1,"Single Room", "This is single room type", false, RoomType.Single));
        roomTypeList.add(new BookingRoomType(2,"Double Room", "This is double room type", false, RoomType.Double));
        roomTypeList.add(new BookingRoomType(3,"Queen Room", "This is queen room type", false, RoomType.Queen));
        roomTypeList.add(new BookingRoomType(4,"King Room", "This is king room type", false, RoomType.King));
        roomTypeList.add(new BookingRoomType(5,"Twin Room", "This is twin room type", false, RoomType.Twin));
        roomTypeList.add(new BookingRoomType(6,"Studio Room", "This is studio room type", false, RoomType.Studio));


        // Handle filter selected room type
        if (bookingFormData.getRoomTypeList() != null) {
            List<Integer> allSelectedIds = this.bookingFormData.getRoomTypeList().stream()
                    .map(BookingRoomType::getId)
                    .collect(Collectors.toList());

            roomTypeList.forEach(roomType -> {
                if (allSelectedIds.contains(roomType.getId())) {
                    roomType.setSelected(true);
                }
            });

            List<BookingRoomType> selectedRoomsType = roomTypeList.stream()
                    .filter(roomType -> allSelectedIds.contains(roomType.getId()))
                    .collect(Collectors.toList());
            List<BookingRoomType> deselectedRoomsType =roomTypeList.stream()
                    .filter(roomType -> !allSelectedIds.contains(roomType.getId()))
                    .collect(Collectors.toList());

            List<BookingRoomType> mergedRoomTypes = new ArrayList<>();
            mergedRoomTypes.addAll(selectedRoomsType);
            mergedRoomTypes.addAll(deselectedRoomsType);

            roomTypeList = (ArrayList<BookingRoomType>) mergedRoomTypes;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.booking_rooms_select_bottom_sheet, container, false);

        // Initialize your RecyclerView and data here
        this.recyclerView = rootView.findViewById(R.id.booking_room_type_recycler_view);

        // Set up the RecyclerView
        BookingRoomTypeAdapter bookingRoomTypeAdapter = new BookingRoomTypeAdapter((BookingActivity) requireContext(), roomTypeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(bookingRoomTypeAdapter);

        // Handle click select button
        selectRoomTypeBtn = rootView.findViewById(R.id.booking_rooms_select_button);
        setUpSelectRoomType();

        return rootView;
    }

    public void setUpSelectRoomType() {
        selectRoomTypeBtn.setOnClickListener(v -> {
            List<BookingRoomType> selectedRoomList = roomTypeList.stream().filter(roomType -> roomType.isSelected() == true).collect(Collectors.toList());
            onSaveClickListener.onSelectClick((ArrayList<BookingRoomType>) selectedRoomList);

            dismiss();
        });
    }
}