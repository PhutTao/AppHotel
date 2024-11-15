package com.example.apphotel.Booking.Data;

import com.example.apphotel.Booking.Item.BookingPaymentMethod;
import com.example.apphotel.Booking.Item.BookingRoomType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class BookingFormDetailData implements Serializable {
    private int hotelId;
    private int selectedRoomValue;
    private int selectedAdultValue;
    private int selectedChildValue;
    private Date startDate;
    private Date endDate;
    private String phoneNumber;
    private ArrayList<BookingRoomType> roomTypeList;
    private BookingPaymentMethod bookingPaymentMethod;

    public ArrayList<BookingRoomType> getRoomTypeList() {
        return roomTypeList;
    }

    public void setRoomTypeList(ArrayList<BookingRoomType> roomTypeList) {
        this.roomTypeList = roomTypeList;
    }

    public BookingFormDetailData() {
        this.selectedRoomValue = 0;
        this.selectedAdultValue = 0;
        this.selectedChildValue = 0;
        this.roomTypeList = new ArrayList<BookingRoomType>();
    }

    public BookingPaymentMethod getBookingPaymentMethod() {
        return bookingPaymentMethod;
    }

    public void setBookingPaymentMethod(BookingPaymentMethod bookingPaymentMethod) {
        this.bookingPaymentMethod = bookingPaymentMethod;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getSelectedRoomValue() {
        return selectedRoomValue;
    }

    public void setSelectedRoomValue(int selectedRoomValue) {
        this.selectedRoomValue = selectedRoomValue;
    }

    public int getSelectedAdultValue() {
        return selectedAdultValue;
    }

    public void setSelectedAdultValue(int selectedAdultValue) {
        this.selectedAdultValue = selectedAdultValue;
    }

    public int getSelectedChildValue() {
        return selectedChildValue;
    }

    public void setSelectedChildValue(int selectedChildValue) {
        this.selectedChildValue = selectedChildValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
