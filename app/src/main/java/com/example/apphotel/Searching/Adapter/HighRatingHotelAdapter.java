package com.example.apphotel.Searching.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.Booking.Activity.BookingActivity;
import com.example.apphotel.R;
import com.example.apphotel.Searching.Activity.DetailActivity;
import com.example.apphotel.Searching.Domain.Hotel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HighRatingHotelAdapter extends RecyclerView.Adapter<HighRatingHotelAdapter.myViewHolder> {
    private Context context;
    private List<Hotel> mListHighRating;

    public HighRatingHotelAdapter(Context context, List<Hotel> mListHighRating) {
        this.context = context;
        this.mListHighRating = mListHighRating;
    }

    @NonNull
    @Override
    public HighRatingHotelAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_high_rating_hotel, parent, false);
        return new myViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HighRatingHotelAdapter.myViewHolder holder, int position) {
        Hotel hotel = mListHighRating.get(position);

        double formattedRate = Math.round(hotel.getRate() * 10.0) / 10.0;


        holder.tvName.setText(hotel.getName());
        holder.tvRating.setText("" + formattedRate);
        holder.tvCount.setText("(" + hotel.getReviewQuantity() + ")");

        // Load image using Picasso
        if (hotel.getImageDetails() != null && !hotel.getImageDetails().isEmpty()) {
            String imageUrl = hotel.getImageDetails().get(0).getImg();
            Picasso.get().load(imageUrl).into(holder.imgHotel);
        } else {
            holder.imgHotel.setImageResource(R.drawable.searching_image_muongthanh);
        }
        holder.cvHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("hotelId", hotel.getId());
                context.startActivity(intent);
            }
        });

        holder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("hotelId", hotel.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListHighRating.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName, tvRating, tvCount;
        AppCompatButton btnBook;
        ImageView imgHotel;
        CardView cvHotel;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_tv_high_rating_hotel_name);
            tvRating = itemView.findViewById(R.id.item_tv_high_rating_hotel_rate);
            tvCount = itemView.findViewById(R.id.item_tv_high_rating_hotel_rate_count);
            btnBook = itemView.findViewById(R.id.item_tv_high_rating_hotel_button);
            imgHotel = itemView.findViewById(R.id.item_img_high_rating_hotel);
            cvHotel = itemView.findViewById(R.id.item_cv_high_rating_hotel);
        }
    }
}
