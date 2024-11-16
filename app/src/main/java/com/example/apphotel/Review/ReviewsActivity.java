package com.example.apphotel.Review;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Login.LoginActivity;
import com.example.apphotel.R;
import com.example.apphotel.Review.ApiService.ICallBack;
import com.example.apphotel.Review.ApiService.ReviewEndpoint;
import com.example.apphotel.Review.AsyncTask.ReviewAsynctask;
import com.example.apphotel.Review.Fragment.ReviewCommentFragment;
import com.example.apphotel.Review.Fragment.ReviewListFragment;
import com.example.apphotel.Review.dto.ReviewResponse;
import com.example.apphotel.Searching.Activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewsActivity extends AppCompatActivity {
    ImageView imageView;
    private Retrofit retrofit;
    private String jwt;

    private Long hotelId;
    String baseUrl;
    private List<ReviewResponse> reviews=new ArrayList<>();
    private Button commentBtn;
    private EditText CommentText;
    private TextView textView;
    private TextView numberReview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluatting_layout);
        baseUrl=getString(R.string.base_url);

        retrofit= new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        imageView=findViewById(R.id.review_back_icon);
         textView=findViewById(R.id.review_StarNumber);
        numberReview=findViewById(R.id.numberReview);



        openReview();


    }

    private void openFragment() {
        try {
        ReviewCommentFragment fragment = new ReviewCommentFragment(hotelId, jwt);
        Log.i("Start openFragment ", "Revies size: " + reviews.size());
        ReviewListFragment reviewListFragment = new ReviewListFragment(reviews);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.replace(R.id.review_list_fragment, reviewListFragment);
        fragmentTransaction.commit();
    } catch (Exception e){
        e.printStackTrace();
    }

    }

    private void openReview() {


        Integer hotelIdInt= getIntent().getIntExtra("hotelId", 0);
        if(hotelIdInt==0){
            hotelId=1L;
        }else {
            hotelId=Long.parseLong(hotelIdInt.toString());
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReviewsActivity.this, DetailActivity.class);
                intent.putExtra("hotelId", Integer.parseInt(hotelId+""));
                startActivity(intent);
            }
        });

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
         jwt= sharedPreferences.getString("jwtKey", "");
        if(jwt.isEmpty()){
            Intent intent=new Intent(ReviewsActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        //-----------------------------
        new ReviewAsynctask(new ICallBack() {
            @Override
            public void onSuccess(List<ReviewResponse> reviewResponses) {
                reviews=reviewResponses;
                Float rate=computeRating(reviews);
                setStarRating(rate);
                numberReview.setText("Base on "+reviewResponses.size()+" reviews");


                openFragment();
            }

            @Override
            public void onFailure() {
                Toast.makeText(ReviewsActivity.this, "Mang yeu. ", Toast.LENGTH_SHORT).show();
                reviews=new ArrayList<ReviewResponse>();
            }
        }, retrofit.create(ReviewEndpoint.class)).execute(jwt, hotelId+"");


        //----------------------------------





    }

    private Float computeRating(List<ReviewResponse> reviews) {

        if(reviews!=null) {
            try {
                float totalRating = reviews.stream().map(ReviewResponse::getRate).reduce(0.f, (a, b) -> a + b).floatValue();
                return totalRating / ((float) reviews.size());
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        return 0.f;
    }

    private void setStarRating(float userRating) {
        // Đánh giá từ người dùng

        // Số ngôi sao cần tô màu (ví dụ: 3.4 sẽ tô màu 4 ngôi sao)
        textView.setText(userRating+"");
        int numberOfStarsToColor = (int) Math.ceil(userRating);

        // Tô màu các ngôi sao tương ứng
        for (int i = 1; i <= 5; i++) {
            ImageView starImageView = findViewById(getResources().getIdentifier("review_star_0" + i, "id", getPackageName()));

            if (i <= numberOfStarsToColor) {
                // Tô màu ngôi sao
                starImageView.setImageResource(R.drawable.review_star);
            } else {
                // Để lại ngôi sao không được tô màu
                starImageView.setImageResource(R.drawable.review_star_regular);
            }
        }
    }
}