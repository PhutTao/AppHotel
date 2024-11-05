package com.example.apphotel.Review.ApiService;

import com.example.apphotel.Review.dto.ReviewResponse;

import java.util.List;

public interface ICallBack {
    void onSuccess(List<ReviewResponse> reviewResponses);
    void onFailure();
}
