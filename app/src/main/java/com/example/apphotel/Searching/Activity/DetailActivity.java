package com.example.apphotel.Searching.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.apphotel.Booking.Activity.BookingActivity;
import com.example.apphotel.Booking.Constants.Constants;
import com.example.apphotel.R;
import com.example.apphotel.Review.ReviewsActivity;
import com.example.apphotel.Searching.Adapter.ReviewHotelAdapter;
import com.example.apphotel.Searching.AsyncTask.DetailHotelApiCallAsyncTask;
import com.example.apphotel.Searching.AsyncTask.ReviewHotelApiCallAsyncTask;
import com.example.apphotel.Searching.Domain.Hotel;
import com.example.apphotel.Searching.Domain.Review;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailHotelApiCallAsyncTask.ApiCallListener, ReviewHotelApiCallAsyncTask.ApiCallListener {
    TextView tvName, tvAddress, tvOverview, tvPrice;
    RecyclerView rvReviewsItem;
    ImageButton detailBackBtn;
    ReviewHotelAdapter reviewHotelAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        rvReviewsItem = findViewById(R.id.detail_rv_reviews_item);
        AppCompatButton bookingBtn = findViewById(R.id.detail_booking_button);
        detailBackBtn = findViewById(R.id.detail_back_button);
        TextView tvReviewsSeeAll = findViewById(R.id.detail_tv_reviews_see_all);

        Intent intent = getIntent();
        int hotelId = intent.getIntExtra("hotelId", -1);
        if (hotelId == -1) {
            Toast.makeText(this, "Hotel ID is missing", Toast.LENGTH_SHORT).show();
            finish();
        }

        getDetailHotel(hotelId);
        getReviewById(hotelId);

        detailBackBtn.setOnClickListener(v -> onBackPressed());
        bookingBtn.setOnClickListener(v -> {
            Intent bookingIntent = new Intent(DetailActivity.this, BookingActivity.class);
            bookingIntent.setAction(Constants.ACTION_DETAIL_TO_BOOKING);
            bookingIntent.putExtra("hotelId", hotelId);
            startActivity(bookingIntent);
        });
        tvReviewsSeeAll.setOnClickListener(v -> {
            Intent reviewIntent = new Intent(DetailActivity.this, ReviewsActivity.class);
            reviewIntent.putExtra("hotelId", hotelId);
            startActivity(reviewIntent);
        });
    }

    private void getReviewById(int hotelId) {
    }

    private void getDetailHotel(int hotelId) {
    }

    @Override
    public void onApiCallSuccess(Hotel hotel) {
        if (hotel == null) {
            Toast.makeText(this, "Failed to fetch hotel details.", Toast.LENGTH_SHORT).show();
            return;
        }

        double formattedPrice = Math.round(hotel.getPrice() / 24237);

        tvName = findViewById(R.id.detail_tv_hotel_name);
        tvAddress = findViewById(R.id.detail_tv_hotel_address);
        tvOverview = findViewById(R.id.detail_tv_overview_content);
        tvPrice = findViewById(R.id.detail_tv_price);

        tvName.setText(hotel.getName());
        tvAddress.setText(hotel.getAddress());
        tvOverview.setText(hotel.getOverview());
        tvPrice.setText("$" + formattedPrice);

        if (hotel.getImageDetails() != null && hotel.getImageDetails().size() >= 3) {
            ImageSlider imageSlider = findViewById(R.id.detail_img_slider);
            ArrayList<SlideModel> slideModels = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                slideModels.add(new SlideModel(hotel.getImageDetails().get(i).getImg(), ScaleTypes.FIT));
            }

            imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        } else {
            Log.e("DetailActivity", "Image details are missing or insufficient.");
        }
    }

    @Override
    public void onApiCallSuccess(List<Review> reviews) {
        if (reviews != null && !reviews.isEmpty()) {
            List<Review> firstTwoReviews = reviews.subList(0, Math.min(2, reviews.size()));
            if (reviewHotelAdapter == null) {
                reviewHotelAdapter = new ReviewHotelAdapter(this, firstTwoReviews);
                rvReviewsItem.setLayoutManager(new LinearLayoutManager(this));
                rvReviewsItem.setAdapter(reviewHotelAdapter);
            } else {
                reviewHotelAdapter.updateReviews(firstTwoReviews);
            }
        } else {
            Toast.makeText(this, "No reviews available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onApiCallFailure(String errorMessage) {
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
        Log.e("API Error", errorMessage);
    }
}