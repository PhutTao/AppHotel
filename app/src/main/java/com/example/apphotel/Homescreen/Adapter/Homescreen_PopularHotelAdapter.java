package com.example.apphotel.Homescreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelsApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.R;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homescreen_PopularHotelAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Homescreen_PopularHotel> popularHotelList;

    public Homescreen_PopularHotelAdapter(Context context, int layout, List<Homescreen_PopularHotel> popularHotelList) {
        this.context = context;
        this.layout = layout;
        this.popularHotelList = popularHotelList;
    }

    @Override
    public int getCount() {
        return popularHotelList.size();
    }

    @Override
    public Object getItem(int i) {
        return popularHotelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView txtTen;
        TextView txtDiaChi;
        ImageView imgHinh;
        TextView txtDanhGia;
        TextView txtSLDanhGia;
        TextView txtGia;
        ImageView heartImageViewTrue;
        ImageView heartImageViewFalse;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.txtTen = convertView.findViewById(R.id.home_name_popularhotel);
            holder.txtGia = convertView.findViewById(R.id.home_price_popularhotel);
            holder.txtSLDanhGia = convertView.findViewById(R.id.home_SLdanhgia_popularhotel);
            holder.txtDanhGia = convertView.findViewById(R.id.home_rate_popularhotel);
            holder.imgHinh = convertView.findViewById(R.id.home_img_popularhotel);
            holder.heartImageViewTrue = convertView.findViewById(R.id.home_tym2);
            holder.heartImageViewFalse = convertView.findViewById(R.id.home_tym);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Homescreen_PopularHotel hotel = popularHotelList.get(position);

        holder.txtTen.setText(hotel.getTen());
        holder.txtGia.setText(String.format("$%.2f/Day", hotel.getGia()));
        holder.txtSLDanhGia.setText(String.format("(%d)", hotel.getSoLuongDanhGia()));
        holder.txtDanhGia.setText(String.format("%.1f⭐", hotel.getDanhGia()));

        // Load image with Picasso or other library
        if (hotel.getHinh() != null) {
            holder.imgHinh.setImageBitmap(hotel.getHinh());
        } else {
            holder.imgHinh.setImageResource(R.drawable.homescreen_meroda);
        }
        if(hotel.isHearted() == true){
            holder.heartImageViewTrue.setVisibility(View.VISIBLE);
            holder.heartImageViewFalse.setVisibility(View.GONE);
        }else{
            holder.heartImageViewTrue.setVisibility(View.GONE);
            holder.heartImageViewFalse.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


}
