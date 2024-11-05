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

import com.example.apphotel.Homescreen.HotelApiService.Home_Booked;
import com.example.apphotel.Homescreen.HotelApiService.Home_BookedApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_Hotel;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiClient;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelApiResponse;
import com.example.apphotel.Homescreen.HotelApiService.Home_HotelEndpoint;
import com.example.apphotel.Homescreen.HotelApiService.Home_ImageDetail;
import com.example.apphotel.R;
import com.example.apphotel.Homescreen.Adapter.Homescreen_BookedAdapter;
import com.example.apphotel.Homescreen.Hotels.Homescreen_Booked;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class Homescreen_mybooking_booked extends Fragment {
    LinearLayout lnBookedHotel;
    ArrayList<Homescreen_Booked> arrayBookedHotel;
    Homescreen_BookedAdapter  adapter;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_mybooking_fragment_booked, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        arrayBookedHotel = new ArrayList<>();
        new BookedsAsyncTask().execute();
        adapter = new Homescreen_BookedAdapter(getActivity(),R.layout.homescreen_item_booked, arrayBookedHotel);

        lnBookedHotel = (LinearLayout) view.findViewById(R.id.lvBookedHotel);


        return view;
    }
    private class BookedsAsyncTask extends AsyncTask<Void, Void, List<Homescreen_Booked>> {
        @Override
        protected List<Homescreen_Booked> doInBackground(Void... voids) {
            List<Homescreen_Booked> result = new ArrayList<>();

            // Retrofit network request
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String jwtToken = sharedPreferences.getString("jwtKey", null);

            Home_HotelEndpoint hotelEndpoint = Home_HotelApiClient.getClient().create(Home_HotelEndpoint.class);
            Call<Home_BookedApiResponse> call = hotelEndpoint.getBooked("Bearer " + jwtToken);

            try {
                Response<Home_BookedApiResponse> response = call.execute();
                if (response.isSuccessful()) {
                    List<Home_Booked> apiBookeds = response.body().getData();
                    for (Home_Booked apiBooked : apiBookeds) {
                        Call<Home_HotelApiResponse> hotelCall = hotelEndpoint.getHotel(apiBooked.getHotelId(),"Bearer " + jwtToken);
                        Response<Home_HotelApiResponse> hotelResponse = hotelCall.execute();
                        if (hotelResponse.isSuccessful()) {
                            Home_Hotel apiHotel = hotelResponse.body().getData();
                            // Convert API Hotel to Homescreen_Booked
                            double formattedRate = Math.round(apiBooked.getHotelRate() * 10.0) / 10.0;
                            double formattedPrice = Math.round(apiHotel.getPrice() / 24237);
                            Homescreen_Booked bookedHotel = new Homescreen_Booked(
                                    apiBooked.getHotelId(),
                                    apiHotel.getName(),
                                    apiHotel.getAddress(),
                                    formattedRate,
                                    apiBooked.getReviewQuantity(),
                                    formattedPrice,
                                    getHinhFromImageDetails(apiHotel.getImageDetails()),
                                    apiBooked.getStartDate(),
                                    apiBooked.getEndDate()

                            );
                            // Add to result list
                            result.add(bookedHotel);

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