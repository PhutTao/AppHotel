package com.example.apphotel.Homescreen.Hotels;

import android.graphics.Bitmap;

import java.text.NumberFormat;
import java.util.Locale;

public class Homescreen_Nearbyhotel {
    private int hotelId;
    private String name;
    private String address;
    private double rating;
    private int reviewCount;
    private double price;
    private Bitmap imageBitmap;
    private String imageUrl;

    // Constructor with Bitmap
    public Homescreen_Nearbyhotel(int hotelId, String name, String address, double rating, int reviewCount, double price, Bitmap imageBitmap) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.price = price;
        this.imageBitmap = imageBitmap;
    }

    // Constructor with URL
    public Homescreen_Nearbyhotel(int hotelId, String name, String address, double rating, int reviewCount, double price, String imageUrl) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // Format price to currency format
    public String getFormattedPrice() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return format.format(price);
    }
}
