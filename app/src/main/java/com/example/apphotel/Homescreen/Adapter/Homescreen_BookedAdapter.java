package com.example.apphotel.Homescreen.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Booked;
import com.example.apphotel.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class Homescreen_BookedAdapter {
    private Context context;
    private int layout;
    private List<Homescreen_Booked> bookedList;
    private int id;
    private String ten;
    private String diaChi;
    private String hinhAnh;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }






}
