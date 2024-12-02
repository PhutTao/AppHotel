package com.example.apphotel.Homescreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    public Object getItem(int position) {
        return popularHotelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView txtName, txtPrice, txtReviewCount, txtRating;
        ImageView imgHotel, imgHeartFilled, imgHeartOutline;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            // Initialize ViewHolder
            holder = new ViewHolder();
            holder.txtName = convertView.findViewById(R.id.home_name_popularhotel);
            holder.txtPrice = convertView.findViewById(R.id.home_price_popularhotel);
            holder.txtReviewCount = convertView.findViewById(R.id.home_SLdanhgia_popularhotel);
            holder.txtRating = convertView.findViewById(R.id.home_rate_popularhotel);
            holder.imgHotel = convertView.findViewById(R.id.home_img_popularhotel);
            holder.imgHeartFilled = convertView.findViewById(R.id.home_tym2);
            holder.imgHeartOutline = convertView.findViewById(R.id.home_tym);

            // Set tag
            convertView.setTag(holder);
        } else {
            // Retrieve ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }

        // Get current hotel
        Homescreen_PopularHotel hotel = popularHotelList.get(position);

        // Bind data
        holder.txtName.setText(hotel.getTen());
        holder.txtPrice.setText(String.format("$%.2f/Day", hotel.getGia()));
        holder.txtReviewCount.setText(String.format("(%d)", hotel.getSoLuongDanhGia()));
        holder.txtRating.setText(String.format("%.1f⭐", hotel.getDanhGia()));

        // Load hotel image
        Picasso.get()
                .load(hotel.getHinh())
                .placeholder(R.drawable.homescreen_muongthanh)
                .error(R.drawable.homescreen_meroda)
                .into(holder.imgHotel);

        // Update heart visibility
        if (hotel.isHearted()) {
            holder.imgHeartFilled.setVisibility(View.VISIBLE);
            holder.imgHeartOutline.setVisibility(View.GONE);
        } else {
            holder.imgHeartFilled.setVisibility(View.GONE);
            holder.imgHeartOutline.setVisibility(View.VISIBLE);
        }

        // Set click listeners for hearts
        holder.imgHeartFilled.setOnClickListener(v -> {
            hotel.setHearted(false);
            notifyDataSetChanged();
        });

        holder.imgHeartOutline.setOnClickListener(v -> {
            hotel.setHearted(true);
            notifyDataSetChanged();
        });

        return convertView;
    }
}
