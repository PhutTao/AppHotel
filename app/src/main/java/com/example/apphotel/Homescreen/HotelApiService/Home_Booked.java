package com.example.apphotel.Homescreen.HotelApiService;

import com.google.gson.annotations.SerializedName;

public class Home_Booked {
    @SerializedName("bookingId")
    private int id;

    @SerializedName("startDate")
    private String ngayCheckIn;

    @SerializedName("endDate")
    private String ngayCheckOut;

    @SerializedName("hotelRate")
    private double danhGia;

    @SerializedName("reviewQuantity")
    private int soLuongDanhGia;

    @SerializedName("hotelId")
    private int hotelId;

    @SerializedName("hotelName")
    private String ten;

    @SerializedName("hotelAddress")
    private String diaChi;

    @SerializedName("hotelPrice")
    private double gia;

    @SerializedName("hotelImage")
    private String hinh;

    // Getter và Setter cho id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho ngày check-in
    public String getNgayCheckIn() {
        return ngayCheckIn;
    }

    public void setNgayCheckIn(String ngayCheckIn) {
        this.ngayCheckIn = ngayCheckIn;
    }

    // Getter và Setter cho ngày check-out
    public String getNgayCheckOut() {
        return ngayCheckOut;
    }

    public void setNgayCheckOut(String ngayCheckOut) {
        this.ngayCheckOut = ngayCheckOut;
    }

    // Getter và Setter cho đánh giá
    public double getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(double danhGia) {
        this.danhGia = danhGia;
    }

    // Getter và Setter cho số lượng đánh giá
    public int getSoLuongDanhGia() {
        return soLuongDanhGia;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        this.soLuongDanhGia = soLuongDanhGia;
    }

    // Getter và Setter cho hotelId
    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    // Getter và Setter cho tên khách sạn
    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    // Getter và Setter cho địa chỉ
    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    // Getter và Setter cho giá
    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    // Getter và Setter cho hình ảnh
    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
}
