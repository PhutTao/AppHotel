package com.example.apphotel.Homescreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;
import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Homescreen_NearbyhotelAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Homescreen_Nearbyhotel> nearbyhotelList;

    public Homescreen_NearbyhotelAdapter(Context context, int layout, List<Homescreen_Nearbyhotel> nearbyhotelList) {
        this.context = context;
        this.layout = layout;
        this.nearbyhotelList = nearbyhotelList;
    }

    @Override
    public int getCount() {
        return nearbyhotelList.size();
    }

    @Override
    public Object getItem(int i) {
        return nearbyhotelList.get(i);
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
        Homescreen_NearbyhotelAdapter.ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new Homescreen_NearbyhotelAdapter.ViewHolder();
            holder.txtTen = convertView.findViewById(R.id.home_name_nearbyhotel);
            holder.txtGia = convertView.findViewById(R.id.home_price_nearbyhotel);
            holder.txtSLDanhGia = convertView.findViewById(R.id.home_SLdanhgia_nearbyhotel);
            holder.txtDanhGia = convertView.findViewById(R.id.home_rate_nearbyhotel);
            holder.imgHinh = convertView.findViewById(R.id.home_img_nearbyhotel);
            convertView.setTag(holder);
        } else {
            holder = (Homescreen_NearbyhotelAdapter.ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu khách sạn từ danh sách
        Homescreen_Nearbyhotel hotel = nearbyhotelList.get(position);

        // Gán thông tin cho TextViews
        holder.txtTen.setText(hotel.getTen());
        holder.txtGia.setText(String.format("%.2f/Day", hotel.getGia()));
        holder.txtSLDanhGia.setText(String.format("(%d)", hotel.getSoLuongDanhGia()));
        holder.txtDanhGia.setText(String.format("%.1f⭐", hotel.getDanhGia()));
        // Kiểm tra và gán hình ảnh
        System.out.println("image" + hotel.getHinh());
        if (hotel.getHinh() != null && !hotel.getHinh().isEmpty()) {

            Picasso.get()
                    .load(hotel.getHinh()) // URL hình ảnh
                    .placeholder(R.drawable.homescreen_muongthanh) // Hình placeholder khi tải
                    .error(R.drawable.homescreen_meroda) // Hình lỗi khi không tải được
                    .into(holder.imgHinh);
        } else {
            holder.imgHinh.setImageResource(R.drawable.homescreen_muongthanh); // Hình mặc định nếu không có hình
        }

        return convertView;
    }
}
