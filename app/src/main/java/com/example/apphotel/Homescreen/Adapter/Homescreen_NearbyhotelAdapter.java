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
        ImageView heartImageView;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new Homescreen_NearbyhotelAdapter.ViewHolder();
            holder.txtTen = view.findViewById(R.id.home_name_nearbyhotel);
            holder.txtDiaChi = view.findViewById(R.id.home_location_nearbyhotel);
            holder.imgHinh = view.findViewById(R.id.home_img_nearbyhotel);
            holder.txtDanhGia = view.findViewById(R.id.home_rate_nearbyhotel);
            holder.txtSLDanhGia = view.findViewById(R.id.home_SLdanhgia_nearbyhotel);
            holder.txtGia = view.findViewById(R.id.home_price_nearbyhotel);


            view.setTag(holder);
        } else {
            holder = (Homescreen_NearbyhotelAdapter.ViewHolder) view.getTag();
        }

        // Gán giá trị
        Homescreen_Nearbyhotel nearbyhotel = nearbyhotelList.get(i);

        holder.txtTen.setText(nearbyhotel.getTen());
        holder.txtDiaChi.setText(nearbyhotel.getDiaChi());
        holder.imgHinh.setImageBitmap(nearbyhotel.getHinh());
        holder.txtDanhGia.setText(String.valueOf(nearbyhotel.getDanhGia()));
        holder.txtSLDanhGia.setText(String.valueOf(nearbyhotel.getSoLuongDanhGia()));
        holder.txtGia.setText(String.valueOf(nearbyhotel.getGia()));

        // Tải hình ảnh từ URL
        Glide.with(context)
                .load(nearbyhotel.getHinh())
                .placeholder(R.drawable.homescreen_haian) // Hình ảnh hiển thị trong khi tải
                .error(R.drawable.homescreen_haian) // Hình ảnh khi xảy ra lỗi
                .into(holder.imgHinh);

        // Xử lý sự kiện khi click vào hình trái tim


        return view;
    }
}