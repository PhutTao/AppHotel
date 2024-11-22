package com.example.apphotel.Homescreen.Hotels;


import android.graphics.Bitmap;

import com.example.apphotel.Homescreen.HotelApiService.Home_ImageDetail;

import java.util.Date;
import java.util.List;

public class Homescreen_Booked {
    private int hotelId;
    private String Ten;
    private String DiaChi;
    private double DanhGia;
    private int SoLuongDanhGia;
    private double Gia;
    private Bitmap Hinh;
    private Date NgayCheckIn;
    private Date NgayCheckOut;

    public Homescreen_Booked(int hotelId, String ten, String diaChi, double danhGia, int soLuongDanhGia, double gia,  Bitmap hinh, Date ngayCheckIn, Date ngayCheckOut) {
        this.hotelId = hotelId;
        Ten = ten;
        DiaChi = diaChi;
        DanhGia = danhGia;
        SoLuongDanhGia = soLuongDanhGia;
        Gia = gia;
        Hinh = hinh;
        NgayCheckIn = ngayCheckIn;
        NgayCheckOut = ngayCheckOut;
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
        return SoLuongDanhGia;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        SoLuongDanhGia = soLuongDanhGia;
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

    public Date getNgayCheckIn() {
        return NgayCheckIn;
    }

    public void setNgayCheckIn(Date ngayCheckIn) {
        NgayCheckIn = ngayCheckIn;
    }

    public Date getNgayCheckOut() {
        return NgayCheckOut;
    }

    public void setNgayCheckOut(Date ngayCheckOut) {
        NgayCheckOut = ngayCheckOut;
    }
}
