package com.example.apphotel.Homescreen.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.apphotel.Homescreen.Adapter.Homescreen_NearbyhotelAdapter;
import com.example.apphotel.Homescreen.HotelApiService.Check_Heart;
import com.example.apphotel.Homescreen.HotelApiService.Favourite_Hotel;
import com.example.apphotel.Homescreen.HotelApiService.Favourite_Hotels_Api_Response;
import com.example.apphotel.Homescreen.HotelApiService.Home_Hotel;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelsApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.HotelApiService.Home_ImageDetail;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;
import com.example.apphotel.R;
import com.example.apphotel.Searching.Activity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class Homescreen_mybooking_history extends Fragment {
    LinearLayout lnHistory;
    ArrayList<Homescreen_Nearbyhotel> arrayHistory;
    Homescreen_NearbyhotelAdapter adapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.homescreen_mybooking_fragment_history, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        arrayHistory = new ArrayList<>();
        new HotelsAsyncTask().execute();

        adapter = new Homescreen_NearbyhotelAdapter(getActivity(),R.layout.homescreen_item_nearbyhotel, arrayHistory);
        lnHistory = (LinearLayout) view.findViewById(R.id.lvHistoryHotel);

        return view;
    }

    private class HotelsAsyncTask extends AsyncTask<Void, Void, List<Homescreen_Nearbyhotel>> {
        @Override
        protected List<Homescreen_Nearbyhotel> doInBackground(Void... voids) {
            List<Homescreen_Nearbyhotel> result = new ArrayList<>();

            // Retrofit network request
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtKey", null);

            Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
            Call<Favourite_Hotels_Api_Response> call = hotelEndpoint.getFavoriteHotels("Bearer " + jwtToken);
            //code dong tren de lay du lieju cac booking yeu thich
            try {
                Response<Favourite_Hotels_Api_Response> response = call.execute();
                if (response.isSuccessful()) {
                    List<Favourite_Hotel> apiHotels = response.body().getData();
                    //danh sach hotel yeu thich
                    for (Favourite_Hotel apiHotel : apiHotels) {
                        //liet ke tung khach sang
                        Call<Home_HotelApiResponse> hotelCall = hotelEndpoint.getDetailHotel(apiHotel.getHotelId(),"Bearer " + jwtToken);
                        Response<Home_HotelApiResponse> hotelResponse = hotelCall.execute();
                        if(hotelResponse.isSuccessful()) {
                            Home_Hotel apiTymHotel = hotelResponse.body().getData();
                            // Convert API Hotel to Homescreen_Nearbyhotel
                            Call<Check_Heart> checkHeartCall = hotelEndpoint.checkHeart(1, apiHotel.getId());
                            Response<Check_Heart> response1 = checkHeartCall.execute();
                            Check_Heart checkHeart = response1.body();
                            double formattedRate = Math.round(apiTymHotel.getRate() * 10.0) / 10.0;

                            Homescreen_Nearbyhotel nearbyHotel = new Homescreen_Nearbyhotel(
                                    apiTymHotel.getId(),
                                    apiTymHotel.getName(),
                                    apiTymHotel.getAddress(),
                                    formattedRate,
                                    apiTymHotel.getReviewQuantity(),
                                    apiTymHotel.getPrice(),
                                    apiTymHotel.getHinh(), // Truyền URL trực tiếp nếu nó là chuỗi
                                    apiTymHotel.isFavourited(),
                                    checkHeart.isSuccess()
                            );
                            // Add to result list
                            result.add(nearbyHotel);
                        }

                    }
                } else {
                    Log.e("API Error", "Error response from API: " + response.message());
                }
            } catch (IOException e) {
                Log.e("API Error", "Exception during API call: " + e.getMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(List<Homescreen_Nearbyhotel> result) {
            if (result != null) {
                arrayHistory.clear();
                arrayHistory.addAll(result);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < adapter.getCount(); i++) {
                    final int position = i;
                    View item = adapter.getView(i, null, null);
                    lnHistory.addView(item);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Lấy ID của view được nhấn
                            int selectedHotelId = arrayHistory.get(position).getHotelId();
                            // Tạo intent để chuyển sang activity chi tiết và gửi ID
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("hotelId", selectedHotelId);
                            startActivity(intent);
                        }
                    });
                }

                // Check and log the contents of arrayNearByHotel
                if (!arrayHistory.isEmpty()) {
                    for (Homescreen_Nearbyhotel hotel : arrayHistory) {
                        Log.d("Hotel Info", "Hotel Name: " + hotel.getTen());
                        Log.d("Hotel Info", "Hotel rate: " + hotel.getDanhGia());
                        Log.d("Hotel Info", "Hotel rate: " + hotel.getSoLuongDanhGia());
                    }
                } else {
                    Log.e("Hotel Info", "arrayNearByHotel is empty");
                }

            } else {
                Log.e("API Error", "Null response received from API");
            }
            progressBar.setVisibility(View.GONE);
        }


    }
    // Method to extract the image URL from ImageDetails
    private Bitmap getHinhFromImageDetails(List<Home_ImageDetail> imageDetails) {
        if (imageDetails != null && !imageDetails.isEmpty()) {
            String imageUrl = imageDetails.get(0).getImageUrl();
            // Use Picasso to load the image asynchronously and return the loaded Bitmap
            return loadBitmapWithPicasso(imageUrl);
        }
        // Return a default Bitmap if no image details are available
        return BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
    }


    // In your loadBitmapWithPicasso method
    private Bitmap loadBitmapWithPicasso(String imageUrl) {
        try {
            return Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.homescreen_muongthanh)  // Set a placeholder image
                    .error(R.drawable.homescreen_meroda)  // Set an error image
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}