package com.example.apphotel.Searching.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.R;
import com.example.apphotel.Searching.Activity.DetailActivity;
import com.example.apphotel.Searching.Domain.Hotel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularHotelAdapter extends RecyclerView.Adapter<PopularHotelAdapter.myViewHolder> {
    Context context;
    List<Hotel> mListPopularHotel;

    public PopularHotelAdapter(Context context, List<Hotel> mListPopularHotel) {
        this.context = context;
        this.mListPopularHotel = mListPopularHotel;
    }

    @NonNull
    @Override
    public PopularHotelAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_popular_hotels, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularHotelAdapter.myViewHolder holder, int position) {
        Hotel hotel = mListPopularHotel.get(position);

        double formattedRate = Math.round(hotel.getRate() * 10.0) / 10.0;
//        double formattedPrice = Math.round(hotel.getPrice() / 24237);

        // Set other data to the views
        holder.tvName.setText(hotel.getName());
        holder.tvAddress.setText(hotel.getAddress());
        holder.tvRating.setText("" + formattedRate);
        holder.tvCount.setText("(" + hotel.getReviewQuantity() + ")");

        // Load image using Picasso
        if (hotel.getImageDetails() != null && !hotel.getImageDetails().isEmpty()) {
            String imageUrl = hotel.getImageDetails().get(0).getImg();
            Picasso.get().load(imageUrl).into(holder.imgHotel);
        } else {
            holder.imgHotel.setImageResource(R.drawable.searching_image_muongthanh);
        }
    }

    @Override
    public int getItemCount() {
        return mListPopularHotel.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvRating, tvCount, tvSeeAll;
        ImageView imgHotel;
        CardView cvHotel;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_tv_recently_viewed_name);
            tvAddress = itemView.findViewById(R.id.item_tv_recently_viewed_address);
            tvRating = itemView.findViewById(R.id.item_tv_recently_viewed_score);
            tvCount = itemView.findViewById(R.id.item_tv_recently_viewed_count);
            imgHotel = itemView.findViewById(R.id.item_img_recently_viewed);
            cvHotel = itemView.findViewById(R.id.item_cv_recently_viewed);
            tvSeeAll = itemView.findViewById(R.id.searching_tv_popular_hotel_see_all);

            cvHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Hotel clickedHotel = mListPopularHotel.get(position);
                        int hotelId = clickedHotel.getId();

                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.putExtra("hotelId", hotelId);

                        // Start DetailActivity
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
