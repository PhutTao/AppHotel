package com.example.apphotel.Homescreen.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Booked;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;

import com.example.apphotel.R;

import java.util.List;

public class Homescreen_NearbyhotelAdapter extends RecyclerView.Adapter<Homescreen_NearbyhotelAdapter.HotelViewHolder> {
    private Context context;
    private List<Homescreen_Nearbyhotel> hotelList;

    public Homescreen_NearbyhotelAdapter(Context context, List<Homescreen_Nearbyhotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    public Homescreen_NearbyhotelAdapter(List<Homescreen_Booked> hotels, int homescreenItemNearbyhotel) {
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homescreen_item_nearbyhotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Homescreen_Nearbyhotel hotel = hotelList.get(position);

        // Set data to views
        holder.name.setText(hotel.getName());
        holder.location.setText(hotel.getAddress());

        // Load image using Glide
        Glide.with(context)
                .load(hotel.getImageUrl())
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
            name = itemView.findViewById(R.id.home_name_nearbyhotel);
            location = itemView.findViewById(R.id.home_location_nearbyhotel);
            image = itemView.findViewById(R.id.home_img_nearbyhotel);
        }
    }
}
