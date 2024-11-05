package com.example.apphotel.Searching.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.R;
import com.example.apphotel.Searching.Adapter.HighRatingHotelAdapter;
import com.example.apphotel.Searching.Adapter.LastSearchAdapter;
import com.example.apphotel.Searching.Adapter.PopularHotelAdapter;
import com.example.apphotel.Searching.AsyncTask.AllHotelApiCallAsyncTask;
import com.example.apphotel.Searching.AsyncTask.PopularHotelApiCallAsyncTask;
import com.example.apphotel.Searching.Domain.Hotel;
import com.example.apphotel.Searching.Domain.LastSearchDomain;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchingActivity extends AppCompatActivity implements PopularHotelApiCallAsyncTask.ApiCallListener, AllHotelApiCallAsyncTask.ApiCallListener {
    private static final int MAX_LAST_SEARCH_ITEMS = 5;

    androidx.appcompat.widget.SearchView searchView;
    RecyclerView rvLastSearch, rvPopularHotel, rvHighRatingHotel;
    LastSearchAdapter lastSearchAdapter;
    PopularHotelAdapter mPopularHotelAdapter;
    HighRatingHotelAdapter highRatingHotelAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_layout);

        searchView = findViewById(R.id.searching_ed_search_box);

        rvLastSearch = findViewById(R.id.searching_rv_last_search);
        initLastSearchRecyclerView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                //  Store keywords to LastSearchAdapter
                ArrayList<LastSearchDomain> existingSearchData = loadLastSearchData();
                existingSearchData.add(new LastSearchDomain(query));
                saveLastSearchData(existingSearchData);
                lastSearchAdapter.updateData(existingSearchData);


                //  Intent to SearchingResultsActivity with hotelId extra
                Intent intent = new Intent(SearchingActivity.this, SearchingResultsActivity.class);
                intent.putExtra("SEARCH_QUERY", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        rvPopularHotel = findViewById(R.id.searching_rv_popular_hotel);
        getAllPopularHotels();

        rvHighRatingHotel = findViewById(R.id.searching_rv_high_rating_hotel);
        getHighRatingHotels();

        TextView clearAllBtn = findViewById(R.id.searching_tv_clear_all);
        clearAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearLastSearchData();
                updateLastSearchRecyclerView();
            }
        });

        TextView seeAllBtn_1 = findViewById(R.id.searching_tv_popular_hotel_see_all);
        seeAllBtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwithToSearchingResultActivity();
            }
        });

        TextView seeAllBtn_2 = findViewById(R.id.searching_tv_high_rating_see_all);
        seeAllBtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwithToSearchingResultActivity();
            }
        });

        //  Intent to HomescreenActivity
        ImageButton returnHomeBtn = findViewById(R.id.searching_back_button);
        returnHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingActivity.this, HomescreenActivity.class);
                startActivity(intent);
            }
        });
        // End onCreate()
    }

    private void SwithToSearchingResultActivity() {
        Intent intent = new Intent(SearchingActivity.this, SearchingResultsActivity.class);
        String NULL_TEXT = "";
        intent.putExtra("SEARCH_QUERY", NULL_TEXT);
        startActivity(intent);
    }



    @SuppressLint("NotifyDataSetChanged")
    private void updateLastSearchRecyclerView() {
        ArrayList<LastSearchDomain> updatedList = loadLastSearchData();
        lastSearchAdapter.setData(updatedList);
        lastSearchAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Clear Successfully", Toast.LENGTH_SHORT).show();
    }

    private void clearLastSearchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("lastSearchData");
        editor.apply();
    }

    private ArrayList<LastSearchDomain> loadLastSearchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("lastSearchData", null);

        Type type = new TypeToken<ArrayList<LastSearchDomain>>() {}.getType();
        if (json != null) {
            ArrayList<LastSearchDomain> lastSearchList = gson.fromJson(json, type);
            return lastSearchList;
        } else {
            return new ArrayList<>();
        }
    }

    private void saveLastSearchData(ArrayList<LastSearchDomain> lastSearchList) {
        if (lastSearchList.size() > MAX_LAST_SEARCH_ITEMS) {
            lastSearchList = new ArrayList<>(lastSearchList.subList(1, MAX_LAST_SEARCH_ITEMS + 1));
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(lastSearchList);
        editor.putString("lastSearchData", json);
        editor.apply();
    }



    private void initLastSearchRecyclerView() {
        ArrayList<LastSearchDomain> arrLastSearchData = loadLastSearchData();;
        Collections.reverse(arrLastSearchData);
        lastSearchAdapter = new LastSearchAdapter(this, arrLastSearchData);
        rvLastSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvLastSearch.setAdapter(lastSearchAdapter);
    }

    private void getAllPopularHotels() {
        new PopularHotelApiCallAsyncTask(this, this).execute();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onApiCallSuccess(List<Hotel> popularHotels) {
        if (popularHotels != null) {
            List<Hotel> firstFiveHotels = popularHotels.subList(0, Math.min(5, popularHotels.size()));
            mPopularHotelAdapter = new PopularHotelAdapter(this, firstFiveHotels);
            mPopularHotelAdapter.notifyDataSetChanged();
            rvPopularHotel.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            rvPopularHotel.setAdapter(mPopularHotelAdapter);
        }
    }

    @Override
    public void onApiCallFailure(String errorMessage) {
        Log.e("API Error! Fail to get all popular hotels", errorMessage);
    }

    private void getHighRatingHotels() {
        new AllHotelApiCallAsyncTask(this, this).execute();
    }

    @Override
    public void onGetAllHotelsCompleted(List<Hotel> hotels) {
        if (hotels != null) {
            Collections.sort(hotels, new Comparator<Hotel>() {
                @Override
                public int compare(Hotel hotel1, Hotel hotel2) {
                    return Double.compare(hotel2.getRate(), hotel1.getRate());
                }
            });

            List<Hotel> firstFiveHighRatingHotels = hotels.subList(0, Math.min(5, hotels.size()));
            highRatingHotelAdapter = new HighRatingHotelAdapter(this, firstFiveHighRatingHotels);
            highRatingHotelAdapter.notifyDataSetChanged();
            rvHighRatingHotel.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            rvHighRatingHotel.setAdapter(highRatingHotelAdapter);
        }
    }

    @Override
    public void onGetAllHotelsFailure(String errorMessage) {
        Log.e("API Error! Fail to get all hotels", errorMessage);
    }
}