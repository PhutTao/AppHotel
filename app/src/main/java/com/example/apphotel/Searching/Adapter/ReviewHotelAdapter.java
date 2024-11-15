package com.example.apphotel.Searching.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.R;
import com.example.apphotel.Searching.Domain.Review;

import java.util.List;

public class ReviewHotelAdapter extends RecyclerView.Adapter<ReviewHotelAdapter.myViewHolder> {
    Context context;
    List<Review> reviews;

    public ReviewHotelAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewHotelAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item_reviews, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHotelAdapter.myViewHolder holder, int position) {
        Review review = reviews.get(position);

        double formattedRate = Math.round(review.getRate() * 10.0) / 10.0;

        // Set other data to the views
        holder.tvName.setText(review.getUsername());
        holder.tvRaing.setText("" + formattedRate);
        holder.tvContent.setText(review.getContent());

        //Convert String to Bitmap and set it to avatarImg
        String encodedImageString = review.getAvatarImg();
        if (encodedImageString != null && !encodedImageString.isEmpty()) {
            // Decode the base64-encoded string to a byte array
            byte[] decodedBytes = Base64.decode(encodedImageString, Base64.DEFAULT);

            // Convert the byte array to a Bitmap
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

            // Set the Bitmap to the ImageView
            holder.avatarImg.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class myViewHolder  extends RecyclerView.ViewHolder {
        TextView tvName, tvContent, tvRaing;
        ImageView avatarImg;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_tv_reviews_name);
            tvContent = itemView.findViewById(R.id.item_tv_reviews_content);
            tvRaing = itemView.findViewById(R.id.item_tv_reviews_score);
            avatarImg = itemView.findViewById(R.id.item_img_reviews_avatar);
        }
    }
}
