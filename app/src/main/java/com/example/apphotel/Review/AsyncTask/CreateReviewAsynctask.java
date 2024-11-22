package com.example.apphotel.Review.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.apphotel.AdditionalProfile.Dto.ResponseData;
import com.example.apphotel.Review.ApiService.ReviewEndpoint;
import com.example.apphotel.Review.Fragment.ReviewCommentFragment;
import com.example.apphotel.Review.ReviewsActivity;
import com.example.apphotel.Review.dto.ReviewRequest;

import retrofit2.Call;
import retrofit2.Response;

public class CreateReviewAsynctask extends AsyncTask<String, Void, Boolean> {
    ReviewCommentFragment.CallBack callBack;
    private String jwt;
    private String comment;
    private Integer rate;
    private Long id;

    private ReviewEndpoint reviewEndpoint;

    public CreateReviewAsynctask(ReviewCommentFragment.CallBack callBack, String jwt, String comment, Integer rate, Long id, ReviewEndpoint reviewEndpoint) {
        this.callBack = callBack;
        this.jwt = jwt;
        this.comment = comment;
        this.rate = rate;
        this.id = id;
        this.reviewEndpoint = reviewEndpoint;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            Call<ResponseData> call=reviewEndpoint.createReview("Bearer "+ jwt, id, new ReviewRequest(comment,rate));
            Response<ResponseData> response=call.execute();
            if(response.isSuccessful()){
                Log.i("SUCCESS: ", ""+ response.code()+" : " + response.message());
                return true;
            }else{
                Log.e("BUG: ", ""+ response.code()+" : " + response.message());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if(success) callBack.onSuccess();
        else callBack.onFailure();
    }
}
