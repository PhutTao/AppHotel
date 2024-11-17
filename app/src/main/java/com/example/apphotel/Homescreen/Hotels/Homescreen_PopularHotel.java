package com.example.apphotel.Homescreen.Hotels;

import android.graphics.Bitmap;

public class Homescreen_PopularHotel {
    private int hotelId;
    private String Ten;
    private String DiaChi;
    private double DanhGia;
    private int ReviewQuantity;
    private double Gia;
    private Bitmap Hinh;
    private boolean isRedHeart;

    public Homescreen_PopularHotel(int hotelId, String ten, String diaChi, double danhGia, int soLuongDanhGia, double gia, Bitmap hinh, boolean isRedHeart) {
        this.hotelId = hotelId;
        Ten = ten;
        DiaChi = diaChi;
        DanhGia = danhGia;
        ReviewQuantity = soLuongDanhGia;
        Gia = gia;
        Hinh = hinh;
        this.isRedHeart = isRedHeart;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public double getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(double danhGia) {
        DanhGia = danhGia;
    }

    public int getSoLuongDanhGia() {
        return ReviewQuantity;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        ReviewQuantity = soLuongDanhGia;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double gia) {
        Gia = gia;
    }

    public Bitmap getHinh() {
        return Hinh;
    }

    public void setHinh(Bitmap hinh) {
        Hinh = hinh;
    }

    public boolean isRedHeart() {
        return isRedHeart;
    }

    public void setRedHeart(boolean redHeart) {
        isRedHeart = redHeart;
    }
}
