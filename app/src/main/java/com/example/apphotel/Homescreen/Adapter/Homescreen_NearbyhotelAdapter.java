package com.example.apphotel.Homescreen.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apphotel.Booking.Adapter.BookingPaymentMethodAdapter;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;

import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
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

    private static class ViewHolder {
        TextView name;
        TextView txtDiaChi;
        ImageView imgHinh;
        TextView txtDanhGia;
        TextView txtSLDanhGia;
        TextView txtGia;
        ImageView heartImageView;
    }

    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holder = new Homescreen_NearbyhotelAdapter.ViewHolder();
            holder.name = view.findViewById(R.id.home_name_nearbyhotel);
            holder.txtDiaChi = view.findViewById(R.id.home_location_nearbyhotel);
            holder.imgHinh = view.findViewById(R.id.home_img_nearbyhotel);
            holder.name = view.findViewById(R.id.home_rate_nearbyhotel);
            holder.name = view.findViewById(R.id.home_SLdanhgia_nearbyhotel);
            holder.name = view.findViewById(R.id.home_price_nearbyhotel);
            holder.heartImageView = view.findViewById(R.id.home_tym);

            view.setTag(holder);
        } else {
            holder = (Homescreen_NearbyhotelAdapter.ViewHolder) view.getTag();
        }

        // Gán giá trị từ đối tượng popularHotel vào các View
        Homescreen_Nearbyhotel nearbyhotel = nearbyHotelList.get(i);
        holder.name.setText(nearbyhotel.getTen());
        holder.txtDiaChi.setText(nearbyhotel.getDiaChi());
        holder.name.setText(String.valueOf(nearbyhotel.getDanhGia()));
        holder.name.setText(String.valueOf(nearbyhotel.getSoLuongDanhGia()));
        holder.name.setText(String.valueOf(nearbyhotel.getGia()));

        // Tải hình ảnh từ URL
        Glide.with(context)
                .load(nearbyhotel.getHinh())
                .placeholder(R.drawable.homescreen_haian) // Hình ảnh hiển thị trong khi tải
                .error(R.drawable.homescreen_haian) // Hình ảnh khi xảy ra lỗi
                .into(holder.imgHinh);



        return view;
    }
}