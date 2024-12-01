package com.example.apphotel.Homescreen.Hotels;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class Homescreen_Nearbyhotel {
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setDanhGia(float danhGia) {
        this.danhGia = danhGia;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        this.soLuongDanhGia = soLuongDanhGia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }



    public boolean isFavourited() {
        return isFavourited;
    }

    public void setFavourited(boolean favourited) {
        isFavourited = favourited;
    }

    private int hotelId;
    private String ten;
    private String diaChi;
    private double danhGia;
    private int soLuongDanhGia;
    private double gia;

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    @SerializedName("hotelImage")
    private String hinh;
    private boolean isFavourited;

    public boolean isHearted() {
        return isHearted;
    }

    public void setHearted(boolean hearted) {
        isHearted = hearted;
    }

    private boolean isHearted;

    // Constructor

    public Homescreen_Nearbyhotel(int hotelId, String ten, String diaChi, double danhGia, int soLuongDanhGia, double gia, String hinh, boolean isFavourited, boolean isHearted) {
        this.hotelId = hotelId;
        this.ten = ten;
        this.diaChi = diaChi;
        this.danhGia = danhGia;
        this.soLuongDanhGia = soLuongDanhGia;
        this.gia = gia;
        this.hinh = hinh;
        this.isFavourited = isFavourited;
        this.isHearted = isHearted;

    }
    public Homescreen_Nearbyhotel(int hotelId, String ten, String diaChi, double danhGia, int soLuongDanhGia, double gia, String hinh) {
        this.hotelId = hotelId;
        this.ten = ten;
        this.diaChi = diaChi;
        this.danhGia = danhGia;
        this.soLuongDanhGia = soLuongDanhGia;
        this.gia = gia;
        this.hinh = hinh;

    }

    public double getGia() {
        return gia;
    }

    public int getSoLuongDanhGia() {
        return soLuongDanhGia;
    }

    public double getDanhGia() {
        return danhGia;
    }
}
