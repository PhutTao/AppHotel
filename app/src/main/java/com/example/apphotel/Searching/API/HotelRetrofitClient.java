package com.example.apphotel.Searching.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelRetrofitClient {
    private static final String BASE_URL = "http://localhost:3000";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
