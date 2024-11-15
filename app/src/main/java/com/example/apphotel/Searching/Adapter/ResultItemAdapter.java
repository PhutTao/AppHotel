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

public class ResultItemAdapter extends RecyclerView.Adapter<ResultItemAdapter.resultItemHolder> {
    private Context context;
    private List<Hotel> mListHotels;

    public ResultItemAdapter(Context context, List<Hotel> mListHotels) {
        this.context = context;
        this.mListHotels = mListHotels;
    }
    @NonNull
    @Override
    public ResultItemAdapter.resultItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_search_result_items, parent, false);
        return new resultItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultItemAdapter.resultItemHolder holder, int position) {
        Hotel hotel = mListHotels.get(position);

        double formattedRate = Math.round(hotel.getRate() * 10.0) / 10.0;
        double formattedPrice = Math.round(hotel.getPrice() / 24237);

        // Set other data to the views
        holder.tvName.setText(hotel.getName());
        holder.tvAddress.setText(hotel.getAddress());
        holder.tvRating.setText("" + formattedRate);
        holder.tvPrice.setText("$" + formattedPrice + "/day");
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
        return mListHotels.size();
    }

    public class resultItemHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPrice, tvRating, tvCount;
        ImageView imgHotel;
        CardView cvHotel;
        public resultItemHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_tv_search_result_name);
            tvAddress = itemView.findViewById(R.id.item_tv_search_result_address);
            tvPrice = itemView.findViewById(R.id.item_tv_search_result_price);
            tvRating = itemView.findViewById(R.id.item_tv_search_result_score);
            tvCount = itemView.findViewById(R.id.item_tv_result_item_count);
            imgHotel = itemView.findViewById(R.id.item_img_search_result);
            cvHotel = itemView.findViewById(R.id.item_cv_search_result);

            cvHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Hotel clickedHotel = mListHotels.get(position);
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
