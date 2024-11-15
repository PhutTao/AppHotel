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

public class Homescreen_BookedAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Homescreen_Booked> bookedList;

    public Homescreen_BookedAdapter(Context context, int layout, List<Homescreen_Booked> bookedList) {
        this.context = context;
        this.layout = layout;
        this.bookedList = bookedList;
    }

    @Override
    public int getCount() {
        return bookedList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        // ánh xạ view
        TextView txtTen = (TextView) view.findViewById(R.id.mybooking_name_booked);
        TextView txtDiaChi = (TextView) view.findViewById(R.id.mybooking_location_booked);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.mybooking_img_booked);
        TextView txtDanhGia = (TextView) view.findViewById(R.id.mybooking_rate_booked);
        TextView txtSLDanhGia = (TextView) view.findViewById(R.id.mybooking_SLdanhgia_booked);
        TextView txtGia = (TextView) view.findViewById(R.id.mybooking_price_booked);
        TextView txtCheckIn = (TextView) view.findViewById(R.id.mybooking_ngaycheckin_booked);
        TextView txtCheckOut = (TextView) view.findViewById(R.id.mybooking_ngaycheckout_booked);

        //gán giá trị
        Homescreen_Booked bookedhotels = bookedList.get(i);

        txtTen.setText(bookedhotels.getTen());
        txtDiaChi.setText(bookedhotels.getDiaChi());
        imgHinh.setImageBitmap(bookedhotels.getHinh());
        txtDanhGia.setText(String.valueOf(bookedhotels.getDanhGia()));
        txtSLDanhGia.setText(String.valueOf(bookedhotels.getSoLuongDanhGia()));
        txtGia.setText(String.valueOf(bookedhotels.getGia()));
        txtCheckIn.setText(new SimpleDateFormat("dd/MM/yyyy").format(bookedhotels.getNgayCheckIn()));
        txtCheckOut.setText(new SimpleDateFormat("dd/MM/yyyy").format(bookedhotels.getNgayCheckOut()));
        return view;
    }
}
