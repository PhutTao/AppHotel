package com.example.apphotel.Homescreen.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apphotel.Model.Hotel;
import com.example.apphotel.R;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private List<Hotel> hotelList;

    public HotelAdapter(List<Hotel> hotelList, int homescreen_item_popularhotel) {
        this.hotelList = hotelList;
    }



    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.homescreen_item_popularhotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.name.setText(hotel.getTen());
        holder.location.setText(hotel.getDiaChi());

        Glide.with(holder.image.getContext())
                .load(hotel.getHinhAnh())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView name, location;
        ImageView image;


        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.home_rate_popularhotel);
            name = itemView.findViewById(R.id.home_SLdanhgia_popularhotel);
            name = itemView.findViewById(R.id.home_name_popularhotel);
            name = itemView.findViewById(R.id.home_price_popularhotel);
            location = itemView.findViewById(R.id.home_location_popularhotel);
            image = itemView.findViewById(R.id.home_img_popularhotel);
            image = itemView.findViewById(R.id. home_tym);


        }
    }
}
