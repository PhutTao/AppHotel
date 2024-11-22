package com.example.apphotel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2/api_hotels/"; // Địa chỉ server Node.js của bạn

    // Lấy Retrofit instance
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Địa chỉ API của server Node.js
                    .addConverterFactory(GsonConverterFactory.create()) // Chuyển đổi JSON thành đối tượng Java
                    .build();
        }
        return retrofit;
    }
}

