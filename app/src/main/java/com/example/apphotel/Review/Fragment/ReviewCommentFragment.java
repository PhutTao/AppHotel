package com.example.apphotel.Review.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.apphotel.R;
import com.example.apphotel.Review.ApiService.ReviewEndpoint;
import com.example.apphotel.Review.AsyncTask.CreateReviewAsynctask;

import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviewCommentFragment extends Fragment {
    private Long hotelId;
    private String jwt;
    private Context context
            ;

    public ReviewCommentFragment() {
    }

    public ReviewCommentFragment(Long hotelId) {
        this.hotelId = hotelId;
    }

    public ReviewCommentFragment(Long hotelId, String jwt) {
        this.hotelId = hotelId;
        this.jwt = jwt;
    }

    public ReviewCommentFragment(Long hotelId, String jwt, Context context) {
        this.hotelId = hotelId;
        this.jwt = jwt;
        this.context = context;
    }

    private ImageView starImageView1;
    private ImageView starImageView2;
    private ImageView starImageView3;
    private ImageView starImageView4;
    private ImageView starImageView5;
    private EditText editText;
    private Retrofit retrofit;
    private Integer rate;
    private Boolean success;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = (View) inflater.inflate(R.layout.evaluating_comment_fragment, null);
        ImageView imageView = view.findViewById(R.id.send_comment_icon);
        editText = view.findViewById(R.id.comment_textBox);

        starImageView1 = view.findViewById(R.id.review_star_01);
        starImageView2 = view.findViewById(R.id.review_star_02);
        starImageView3 = view.findViewById(R.id.review_star_03);
        starImageView4 = view.findViewById(R.id.review_star_04);
        starImageView5 = view.findViewById(R.id.review_star_05);
        starImageView1.setOnClickListener(evaluate());
        starImageView2.setOnClickListener(evaluate());
        starImageView3.setOnClickListener(evaluate());
        starImageView4.setOnClickListener(evaluate());
        starImageView5.setOnClickListener(evaluate());

        imageView.setOnClickListener(sendReview());


        return view;
    }

    private View.OnClickListener sendReview() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // goi api đi.
                retrofit = new Retrofit.Builder().baseUrl(getString(R.string.base_url))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                String comment = editText.getText().toString();
                //id
                //rate

                new CreateReviewAsynctask(new CallBack() {
                    @Override
                    public void onSuccess() {
                        success = true;
                        Intent intent = getActivity().getIntent();
                        intent.putExtra("hotelId",Integer.parseInt(hotelId+""));
                        getActivity().finish();
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure() {
                        success = false;
                    }
                }, jwt, comment, rate, hotelId, retrofit.create(ReviewEndpoint.class)).execute();


                editText.setText("");
                starImageView1.setImageResource(R.drawable.review_star_regular);
                starImageView3.setImageResource(R.drawable.review_star_regular);
                starImageView2.setImageResource(R.drawable.review_star_regular);
                starImageView4.setImageResource(R.drawable.review_star_regular);
                starImageView5.setImageResource(R.drawable.review_star_regular);
            }
        };
    }

    private View.OnClickListener evaluate() {
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view == starImageView1) {
                    rate = 1;
                    starImageView1.setImageResource(R.drawable.review_star);
                    starImageView3.setImageResource(R.drawable.review_star_regular);
                    starImageView2.setImageResource(R.drawable.review_star_regular);
                    starImageView4.setImageResource(R.drawable.review_star_regular);
                    starImageView5.setImageResource(R.drawable.review_star_regular);
                } else if (view == starImageView2) {
                    rate = 2;
                    starImageView1.setImageResource(R.drawable.review_star);
                    starImageView2.setImageResource(R.drawable.review_star);
                    starImageView3.setImageResource(R.drawable.review_star_regular);
                    starImageView4.setImageResource(R.drawable.review_star_regular);
                    starImageView5.setImageResource(R.drawable.review_star_regular);

                } else if (view == starImageView3) {
                    rate = 3;
                    starImageView1.setImageResource(R.drawable.review_star);
                    starImageView3.setImageResource(R.drawable.review_star);
                    starImageView2.setImageResource(R.drawable.review_star);
                    starImageView5.setImageResource(R.drawable.review_star_regular);
                    starImageView4.setImageResource(R.drawable.review_star_regular);

                } else if (view == starImageView4) {
                    rate = 4;
                    starImageView1.setImageResource(R.drawable.review_star);
                    starImageView3.setImageResource(R.drawable.review_star);
                    starImageView2.setImageResource(R.drawable.review_star);
                    starImageView4.setImageResource(R.drawable.review_star);
                    starImageView5.setImageResource(R.drawable.review_star_regular);


                } else if (view == starImageView5) {
                    rate = 5;
                    starImageView1.setImageResource(R.drawable.review_star);
                    starImageView3.setImageResource(R.drawable.review_star);
                    starImageView2.setImageResource(R.drawable.review_star);
                    starImageView4.setImageResource(R.drawable.review_star);
                    starImageView5.setImageResource(R.drawable.review_star);
                }
            }
        };
        return clickListener;
    }
    public Boolean getResult(){return success;}

    public interface CallBack {
        void onSuccess();

        void onFailure();
    }

}
