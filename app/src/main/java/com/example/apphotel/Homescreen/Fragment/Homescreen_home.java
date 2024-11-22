package com.example.apphotel.Homescreen.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import android.widget.ImageView;
import android.graphics.Color;
import android.view.ViewTreeObserver;
import android.content.Intent;
import com.example.apphotel.Homescreen.Activity.Homescreen_myprofile;
import com.example.apphotel.Homescreen.Adapter.Homescreen_NearbyhotelAdapter;
import com.example.apphotel.Homescreen.Adapter.Homescreen_PopularHotelAdapter;
import com.example.apphotel.Homescreen.HotelApiService.Home_Hotel;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelsApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.HotelApiService.Home_ImageDetail;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Nearbyhotel;
import com.example.apphotel.Homescreen.Hotels.Homescreen_PopularHotel;
import com.example.apphotel.R;
import com.example.apphotel.Searching.Activity.DetailActivity;
import com.example.apphotel.Searching.Activity.SearchingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;





public class Homescreen_home extends Fragment {
    HorizontalScrollView horizontalScrollView;
    LinearLayout lnNearbyHotel,lnPopularHotel,lnLocation;
    TextView nearbyHotels,txtLocation;
    ScrollView scrollview;

    ImageView btn_seach, avt_user;
    RelativeLayout btn_acc;
    BottomNavigationView bottomNavigationView;
    ArrayList<Homescreen_Nearbyhotel> arrayNearByHotel;
    ArrayList<Homescreen_PopularHotel> arrayPopularHotel;
    Homescreen_NearbyhotelAdapter adapter;
    Homescreen_PopularHotelAdapter adapter_1;
    ProgressBar progressBar, progressBar_1;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_fragment_home, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar_1 = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        avt_user = view.findViewById(R.id.home_img_accout);
        loadUserAvatar();



        arrayNearByHotel = new ArrayList<>();
        arrayPopularHotel = new ArrayList<>();
        new PpHotelsAsyncTask().execute();
        new HotelsAsyncTask().execute();

        adapter = new Homescreen_NearbyhotelAdapter(getActivity(), R.layout.homescreen_item_nearbyhotel, arrayNearByHotel);


        adapter_1 = new Homescreen_PopularHotelAdapter(getActivity(), R.layout.homescreen_item_popularhotel, arrayPopularHotel);

        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.homescreen_horizontal_scroll_view);

        lnNearbyHotel = (LinearLayout) view.findViewById(R.id.home_lvNearbyHotel);

        lnPopularHotel = (LinearLayout) view.findViewById(R.id.home_lvpopularhotel);





        //ImageButton accout
        btn_acc = (RelativeLayout) view.findViewById(R.id.home_btn_acc);

        // Set a click listener for the button
        btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Homescreen_myprofile.class);
                startActivity(intent);
            }
        });



        //đổi màu textnearbyhotel
        nearbyHotels = view.findViewById(R.id.home_nearbyhotels);
        scrollview = view.findViewById(R.id.home_contentt);

        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollview.getScrollY();
                if (scrollY > (dpToPx(390)-scrollview.getHeight()/2)) {
                    nearbyHotels.setTextColor(Color.WHITE);
                } else {
                    nearbyHotels.setTextColor(Color.BLACK);
                }
            }
        });


        //Intent searching
        btn_seach = (ImageView) view.findViewById(R.id.home_btn_search);
        btn_seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchingActivity.class);
                startActivity(intent);
            }
        });



        return view;
    }
    private class PpHotelsAsyncTask extends AsyncTask<Void, Void, List<Homescreen_PopularHotel>> {
        @Override
        protected List<Homescreen_PopularHotel> doInBackground(Void... voids) {
            List<Homescreen_PopularHotel> result = new ArrayList<>();
            // Retrofit network request
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtKey", null);

            Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
            Call<Home_HotelsApiResponse> call = hotelEndpoint.getPpHotels("Bearer " + jwtToken);
            try {
                Response<Home_HotelsApiResponse> response = call.execute();
                if (response.isSuccessful()) {
                    List<Home_Hotel> apiHotels = response.body().getData();
                    for (Home_Hotel apiHotel : apiHotels) {
                        // Convert API Hotel to Homescreen_Nearbyhotel
                        double formattedRate = Math.round(apiHotel.getRate() * 10.0) / 10.0;
                        double formattedPrice = Math.round(apiHotel.getGia() / 24237);
                        Homescreen_PopularHotel popularHotel = new Homescreen_PopularHotel(
                                apiHotel.getId(),
                                apiHotel.getName(),
                                apiHotel.getAddress(),
                                formattedRate,
                                apiHotel.getReviewQuantity(),
                                formattedPrice,
                                getHinhFromImageDetails(apiHotel.getImageDetails()),
                                apiHotel.isFavourited()
                        );

                        // Add to result list
                        result.add(popularHotel);
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
        protected void onPostExecute(List<Homescreen_PopularHotel> result) {
            if (result != null) {
                arrayPopularHotel.clear();
                arrayPopularHotel.addAll(result);
                adapter_1.notifyDataSetChanged();
                for (int i = 0; i < adapter_1.getCount(); i++) {
                    final int position = i;
                    View item = adapter_1.getView(i, null, null);
                    lnPopularHotel.addView(item);
                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Lấy ID của view được nhấn
                            int selectedHotelId = arrayPopularHotel.get(position).getHotelId();
                            // Tạo intent để chuyển sang activity chi tiết và gửi ID
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("hotelId", selectedHotelId);
                            startActivity(intent);
                        }
                    });
                }
            }
            progressBar_1.setVisibility(View.GONE);

        }
    }
    private class HotelsAsyncTask extends AsyncTask<Void, Void, List<Homescreen_Nearbyhotel>> {
        @Override
        protected List<Homescreen_Nearbyhotel> doInBackground(Void... voids) {
            List<Homescreen_Nearbyhotel> result = new ArrayList<>();

            // Retrofit network request
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtKey", null);

            Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
            Call<Home_HotelsApiResponse> call = hotelEndpoint.getHotels("Bearer " + jwtToken);

            try {
                Response<Home_HotelsApiResponse> response = call.execute();
                if (response.isSuccessful()) {
                    List<Home_Hotel> apiHotels = response.body().getData();
                    for (Home_Hotel apiHotel : apiHotels) {
                        // Convert API Hotel to Homescreen_Nearbyhotel
                        double formattedRate = Math.round(apiHotel.getRate() * 10.0) / 10.0;
                        double formattedPrice = Math.round(apiHotel.getGia() / 24237);
                        Homescreen_Nearbyhotel nearbyHotel = new Homescreen_Nearbyhotel(
                                apiHotel.getId(),
                                apiHotel.getName(),
                                apiHotel.getAddress(),
                                formattedRate,
                                apiHotel.getReviewQuantity(),
                                formattedPrice,
                                getHinhFromImageDetails(apiHotel.getImageDetails())
                        );

                        // Add to result list
                        result.add(nearbyHotel);
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
                arrayNearByHotel.clear();
                arrayNearByHotel.addAll(result);
                adapter.notifyDataSetChanged();
                for (int i = 0; i < adapter.getCount(); i++) {
                    final int position = i;
                    View item = adapter.getView(i, null, null);
                    lnNearbyHotel.addView(item);

                    item.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Lấy ID của view được nhấn
                            int selectedHotelId = arrayNearByHotel.get(position).getHotelId();
                            // Tạo intent để chuyển sang activity chi tiết và gửi ID
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("hotelId", selectedHotelId);
                            startActivity(intent);
                        }
                    });
                }

                // Check and log the contents of arrayNearByHotel
                if (!arrayNearByHotel.isEmpty()) {
                    for (Homescreen_Nearbyhotel hotel : arrayNearByHotel) {
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


    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
    private void loadUserAvatar() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String jwtToken = sharedPreferences.getString("jwtKey", null);

        Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
        Call<ResponseBody> avtCall = hotelEndpoint.getUserAvatar("Bearer " + jwtToken);

        avtCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    avt_user.setImageBitmap(bitmap);
                } else {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }


}