package com.example.apphotel.Homescreen.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;

import com.example.apphotel.R;

import java.util.List;

public class Homescreen_NearbyhotelAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Homescreen_Nearbyhotel> nearbyHotelList;

    public Homescreen_NearbyhotelAdapter(Context context, int layout, List<Homescreen_Nearbyhotel> nearbyHotelList) {
        this.context = context;
        this.layout = layout;
        this.nearbyHotelList = nearbyHotelList;
    }

    @Override
    public int getCount() {
        return nearbyHotelList.size();
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
        TextView txtTen = (TextView) view.findViewById(R.id.home_name_nearbyhotel);
        TextView txtDiaChi = (TextView) view.findViewById(R.id.home_location_nearbyhotel);
        ImageView imgHinh = (ImageView) view.findViewById(R.id.home_img_nearbyhotel);
        TextView txtDanhGia = (TextView) view.findViewById(R.id.home_rate_nearbyhotel);
        TextView txtSLDanhGia = (TextView) view.findViewById(R.id.home_SLdanhgia_nearbyhotel);
        TextView txtGia = (TextView) view.findViewById(R.id.home_price_nearbyhotel);


        //gán giá trị
        Homescreen_Nearbyhotel nearbyhotel = nearbyHotelList.get(i);

        txtTen.setText(nearbyhotel.getTen());
        txtDiaChi.setText(nearbyhotel.getDiaChi());
        imgHinh.setImageBitmap(nearbyhotel.getHinh());
        txtDanhGia.setText(String.valueOf(nearbyhotel.getDanhGia()));
        txtSLDanhGia.setText(String.valueOf(nearbyhotel.getSoLuongDanhGia()));
        txtGia.setText(String.valueOf(nearbyhotel.getGia()));
        return view;
    }
}
