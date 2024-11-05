package com.example.apphotel.Booking.Interface;

import com.example.apphotel.Booking.Item.BookingRoomType;

import java.util.ArrayList;

public interface OnSaveClickListener {
    void onSaveClick(int totalGuests, int totalRooms);

    void onSelectClick(ArrayList<BookingRoomType> roomTypeList);
}
