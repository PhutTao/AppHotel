package com.example.apphotel.Homescreen.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelsApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Booked;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;
import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.R;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homescreen_PopularHotelAdapter extends RecyclerView.Adapter<Homescreen_NearbyhotelAdapter.HotelViewHolder> {
    private Context context;
    private List<Homescreen_Nearbyhotel> hotelList;


    public Homescreen_PopularHotelAdapter(List<Homescreen_Booked> hotels, int homescreenItemPopularhotel) {
    }

    public Homescreen_PopularHotelAdapter(Context context, List<Homescreen_PopularHotel> popularHotels) {
    }

    @NonNull
    @Override
    public Homescreen_NearbyhotelAdapter.HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homescreen_item_popularhotel, parent, false);
        return new Homescreen_NearbyhotelAdapter.HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Homescreen_NearbyhotelAdapter.HotelViewHolder holder, int position) {
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
            name = itemView.findViewById(R.id.home_name_popularhotel);
            location = itemView.findViewById(R.id.home_location_popularhotel);
            image = itemView.findViewById(R.id.home_img_popularhotel);
        }
    }
}