package com.example.apphotel.Booking.Item;

import com.example.apphotel.Booking.Enum.RoomType;

import java.io.Serializable;

public class BookingRoomType implements Serializable {
    private int id;
    private String name;
    private String description;
    private boolean isSelected;
    private RoomType roomType;

    public BookingRoomType(int id, String name, String description, boolean isSelected, RoomType roomType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isSelected = isSelected;
        this.roomType = roomType;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
