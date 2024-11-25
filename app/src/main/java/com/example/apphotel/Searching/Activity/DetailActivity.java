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
import com.example.apphotel.Homescreen.Fragment.Homescreen_home;
import com.example.apphotel.MainActivity;
import com.example.apphotel.R;
import com.example.apphotel.Review.ReviewsActivity;
import com.example.apphotel.Searching.Adapter.ResultItemAdapter;
import com.example.apphotel.Searching.Adapter.ReviewHotelAdapter;
import com.example.apphotel.Searching.AsyncTask.DetailHotelApiCallAsyncTask;
import com.example.apphotel.Searching.AsyncTask.ReviewHotelApiCallAsyncTask;
import com.example.apphotel.Searching.Domain.Hotel;
import com.example.apphotel.Searching.Domain.Review;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements DetailHotelApiCallAsyncTask.ApiCallListener {
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
        detailBackBtn = findViewById(R.id.detail_back_button);
        TextView tvReviewsSeeAll = (TextView) findViewById(R.id.detail_tv_reviews_see_all);


        int hotelId;
        Intent intent = getIntent();
        if (intent.getAction() != null && intent.getAction().toString().equals(Constants.ACTION_BOOKING_TO_DETAIL)) {
            hotelId = intent.getIntExtra("hotelId", 0);
        } else {
            hotelId = intent.getIntExtra("hotelId", 0);
        }

        getDetailHotel(hotelId);


        detailBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, Homescreen_home.class);
                startActivity(intent);
            }
        });

        bookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, BookingActivity.class);
                intent.setAction(Constants.ACTION_DETAIL_TO_BOOKING);
                intent.putExtra("hotelId", hotelId);
                startActivity(intent);
            }
        });

        tvReviewsSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, ReviewsActivity.class);
                intent.putExtra("hotelId", hotelId);
                startActivity(intent);
            }
        });
        //End onCreate()
    }



    private void getDetailHotel(int hotelId) {
        new DetailHotelApiCallAsyncTask(this, this).execute(hotelId);
    }

    @Override
    public void onApiCallSuccess(Hotel hotel) {
        if (hotel != null) {
            double formattedPrice = Math.round(hotel.getPrice()
            );

            tvName = findViewById(R.id.detail_tv_hotel_name);
            tvAddress = findViewById(R.id.detail_tv_hotel_address);
            tvOverview = findViewById(R.id.detail_tv_overview_content);
            tvPrice = findViewById(R.id.detail_tv_price);

            tvName.setText(hotel.getName());
            tvAddress.setText(hotel.getAddress());
            tvOverview.setText(hotel.getOverview());
            tvPrice.setText("$" + formattedPrice);

            // Xử lý danh sách ảnh
            if (hotel.getImageDetails() != null && hotel.getImageDetails().size() >= 3) {
                ImageSlider imageSlider = findViewById(R.id.detail_img_slider);
                ArrayList<SlideModel> slideModels = new ArrayList<>();
                slideModels.add(new SlideModel(hotel.getImageDetails().get(0).getImg(), ScaleTypes.FIT));
                slideModels.add(new SlideModel(hotel.getImageDetails().get(1).getImg(), ScaleTypes.FIT));
                slideModels.add(new SlideModel(hotel.getImageDetails().get(2).getImg(), ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);
            } else {
                Log.e("Image Error", "Không đủ ảnh cho slider.");
            }
        } else {
            Log.e("API Error", "Dữ liệu khách sạn null.");
        }
    }




    @Override
    public void onApiCallFailure(String errorMessage) {
        Toast.makeText(this, "Lỗi tải dữ liệu: " + errorMessage, Toast.LENGTH_LONG).show();
        Log.e("API Error", errorMessage);
    }
}