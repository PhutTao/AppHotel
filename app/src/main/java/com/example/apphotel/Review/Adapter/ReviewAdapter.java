package com.example.apphotel.Review.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.R;
import com.example.apphotel.Review.dto.ReviewResponse;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<ReviewResponse> reviews ;

    public ReviewAdapter(List<ReviewResponse> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("ReviewAdapter",reviews.size()+"");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
        return new ReviewViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        ReviewResponse review = reviews.get(position);
        holder.bind(review);
    }
    @Override
    public int getItemCount() {
        return reviews.size();
    }
    class ReviewViewHolder extends RecyclerView.ViewHolder {
        private TextView reviewerNameTextView;
        private TextView ratingTextView;
        private ImageView evaluatedStarImageView;
        private ImageView avatarReviewer;
        private TextView reviewTextTextView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các phần tử trong mẫu XML vào đây
            reviewerNameTextView = itemView.findViewById(R.id.review_item_view_name);
            ratingTextView = itemView.findViewById(R.id.review_item_view_rating);
            evaluatedStarImageView = itemView.findViewById(R.id.review_item_view_img);
            reviewTextTextView = itemView.findViewById(R.id.review_item_view_reviews);
            avatarReviewer=itemView.findViewById(R.id.review_item_avatar);
        }

        public void bind(ReviewResponse review) {
            // Ánh xạ dữ liệu từ đánh giá vào các phần tử của mẫu
            reviewerNameTextView.setText(review.getUsername());
            ratingTextView.setText(String.valueOf(review.getRate()));
            evaluatedStarImageView.setImageResource(R.drawable.review_star);
            reviewTextTextView.setText(review.getContent());
            Bitmap bitmap= BitmapFactory.decodeByteArray(review.getAvatarImg(),0,review.getAvatarImg().length);
            avatarReviewer.setImageBitmap(bitmap);
        }
    }
}

