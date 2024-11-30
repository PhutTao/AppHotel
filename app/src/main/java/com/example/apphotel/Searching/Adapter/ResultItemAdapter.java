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

public class ResultItemAdapter extends RecyclerView.Adapter<ResultItemAdapter.ResultItemHolder> {

    private Context context;
    private List<Hotel> mListHotels;

    public ResultItemAdapter(Context context, List<Hotel> mListHotels) {
        this.context = context;
        this.mListHotels = mListHotels;
    }

    @NonNull
    @Override
    public ResultItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.searching_item_search_result_items, parent, false);
        return new ResultItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultItemHolder holder, int position) {
        Hotel hotel = mListHotels.get(position);

        // Format dữ liệu
        double formattedRate = Math.round(hotel.getRate() * 10.0) / 10.0;
        double formattedPrice = Math.round(hotel.getPrice() );

        // Đặt dữ liệu vào View
        holder.tvName.setText(hotel.getName());
        holder.tvAddress.setText(hotel.getAddress());
        holder.tvRating.setText(String.valueOf(formattedRate));
        holder.tvPrice.setText("$" + formattedPrice + "/day");
        holder.tvCount.setText("(" + hotel.getReviewQuantity() + " reviews)");

        // Load hình ảnh khách sạn
        if (hotel.getImageDetails() != null && !hotel.getImageDetails().isEmpty()) {
            String imageUrl = hotel.getImageDetails().get(0).getImg();
            Picasso.get().load(imageUrl).placeholder(R.drawable.homescreen_haian).into(holder.imgHotel);
        } else {
            holder.imgHotel.setImageResource(R.drawable.searching_image_muongthanh); // Hình ảnh mặc định
        }
    }

    @Override
    public int getItemCount() {
        return mListHotels != null ? mListHotels.size() : 0;
    }

    public void updateData(List<Hotel> newHotels) {
        this.mListHotels = newHotels;
        notifyDataSetChanged();
    }

    public class ResultItemHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvPrice, tvRating, tvCount;
        ImageView imgHotel;
        CardView cvHotel;

        public ResultItemHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_tv_search_result_name);
            tvAddress = itemView.findViewById(R.id.item_tv_search_result_address);
            tvPrice = itemView.findViewById(R.id.item_tv_search_result_price);
            tvRating = itemView.findViewById(R.id.item_tv_search_result_score);
            tvCount = itemView.findViewById(R.id.item_tv_result_item_count);
            imgHotel = itemView.findViewById(R.id.item_img_search_result);
            cvHotel = itemView.findViewById(R.id.item_cv_search_result);

            cvHotel.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Hotel clickedHotel = mListHotels.get(position);
                    int hotelId = clickedHotel.getId();

                    // Chuyển sang DetailActivity
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("hotelId", hotelId);
                    context.startActivity(intent);
                }
            });
        }
    }
}
