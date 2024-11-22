package com.example.apphotel.Review.AsyncTask;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.apphotel.AdditionalProfile.Dto.ResponseData;
import com.example.apphotel.Review.ApiService.ICallBack;
import com.example.apphotel.Review.ApiService.ReviewEndpoint;
import com.example.apphotel.Review.dto.ReviewResponse;
import com.google.gson.internal.LinkedTreeMap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import retrofit2.Call;
import retrofit2.Response;

public class ReviewAsynctask extends AsyncTask<String, Void, List<ReviewResponse>> {
    private ICallBack callback;
    private ReviewEndpoint reviewEndpoint;

    public ReviewAsynctask(ICallBack callback, ReviewEndpoint reviewEndpoint) {
        this.callback = callback;
        this.reviewEndpoint = reviewEndpoint;
    }

    @Override
    protected List<ReviewResponse> doInBackground(String... strings) {
        String jwt= "Bearer "+ strings[0];
        String hotelId= strings[1];
        Call<ResponseData> call=reviewEndpoint.getReview(jwt,Long.parseLong(hotelId));
        try {
            Log.i("Result ", "Cố gắng đi vào .");

            Response<ResponseData> response=call.execute();
            if(response.isSuccessful() && response.code()==200){
                List<LinkedTreeMap<String, String>> reviewLinkedHashMap= (List<LinkedTreeMap<String, String>>) response.body().getData();
                Log.i("Result Size: ", "Size: "+ reviewLinkedHashMap.size());

                List<ReviewResponse> reviewResponses=reviewLinkedHashMap.stream()
                        .map(reviewLinked->{
                            ReviewResponse reviewResponse= new ReviewResponse();
                            reviewResponse.setId((long) Double.parseDouble(String.valueOf(reviewLinked.get("id"))));
                            reviewResponse.setContent(String.valueOf(reviewLinked.get("content")));
                            reviewResponse.setRate(Float.parseFloat(String.valueOf(reviewLinked.get("rate"))));
                            reviewResponse.setUsername(String.valueOf(reviewLinked.get("username")));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                reviewResponse.setAvatarImg(Base64.getDecoder().decode(reviewLinked.get("avatarImg").getBytes()));
                            }
                            Log.i("Data","IMG BYTE[] "+ reviewLinked.get("avatarImg").getBytes());
                            return reviewResponse;
                        }).collect(Collectors.toList());



                return reviewResponses;
            }else{
                Log.e("Loi roi ... ...", "status: "+ response.code() + " | message= "+ response.message() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<ReviewResponse>();
    }

    @Override
    protected void onPostExecute(List<ReviewResponse> reviewResponses) {
        super.onPostExecute(reviewResponses);
        callback.onSuccess(reviewResponses);

    }
}
