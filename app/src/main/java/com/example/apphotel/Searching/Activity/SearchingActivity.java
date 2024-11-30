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

public class SearchingActivity extends AppCompatActivity
        implements PopularHotelApiCallAsyncTask.ApiCallListener, AllHotelApiCallAsyncTask.ApiCallListener {

    private static final int MAX_LAST_SEARCH_ITEMS = 5;

    private SearchView searchView;
    private RecyclerView rvLastSearch, rvPopularHotel, rvHighRatingHotel;
    private LastSearchAdapter lastSearchAdapter;
    private PopularHotelAdapter mPopularHotelAdapter;
    private HighRatingHotelAdapter highRatingHotelAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_layout);

        initViews();
        setupListeners();
        initLastSearchRecyclerView();

        getAllPopularHotels();
        getHighRatingHotels();
    }

    private void initViews() {
        searchView = findViewById(R.id.searching_ed_search_box);
        rvLastSearch = findViewById(R.id.searching_rv_last_search);
        rvPopularHotel = findViewById(R.id.searching_rv_popular_hotel);
        rvHighRatingHotel = findViewById(R.id.searching_rv_high_rating_hotel);
    }

    private void setupListeners() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public boolean onQueryTextSubmit(String query) {
                saveSearchQuery(query);
                navigateToResults(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        findViewById(R.id.searching_tv_clear_all).setOnClickListener(v -> {
            clearLastSearchData();
            updateLastSearchRecyclerView();
        });

        View.OnClickListener seeAllListener = v -> switchToSearchingResultActivity();
        findViewById(R.id.searching_tv_popular_hotel_see_all).setOnClickListener(seeAllListener);
        findViewById(R.id.searching_tv_high_rating_see_all).setOnClickListener(seeAllListener);

        findViewById(R.id.searching_back_button).setOnClickListener(v -> {
            Intent intent = new Intent(SearchingActivity.this, HomescreenActivity.class);
            startActivity(intent);
        });
    }

    private void saveSearchQuery(String query) {
        ArrayList<LastSearchDomain> existingSearchData = loadLastSearchData();
        existingSearchData.add(new LastSearchDomain(query));
        saveLastSearchData(existingSearchData);
        lastSearchAdapter.updateData(existingSearchData);
    }

    private void navigateToResults(String query) {
        Intent intent = new Intent(SearchingActivity.this, SearchingResultsActivity.class);
        intent.putExtra("SEARCH_QUERY", query);
        startActivity(intent);
    }

    private void switchToSearchingResultActivity() {
        Intent intent = new Intent(SearchingActivity.this, SearchingResultsActivity.class);
        intent.putExtra("SEARCH_QUERY", "");
        startActivity(intent);
    }

    private void updateLastSearchRecyclerView() {
        ArrayList<LastSearchDomain> updatedList = loadLastSearchData();
        lastSearchAdapter.setData(updatedList);
        lastSearchAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Clear Successfully", Toast.LENGTH_SHORT).show();
    }

    private void clearLastSearchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("lastSearchData").apply();
    }

    private ArrayList<LastSearchDomain> loadLastSearchData() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("lastSearchData", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<LastSearchDomain>>() {}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }

    private void saveLastSearchData(ArrayList<LastSearchDomain> lastSearchList) {
        if (lastSearchList.size() > MAX_LAST_SEARCH_ITEMS) {
            lastSearchList = new ArrayList<>(lastSearchList.subList(1, MAX_LAST_SEARCH_ITEMS + 1));
        }
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String json = new Gson().toJson(lastSearchList);
        sharedPreferences.edit().putString("lastSearchData", json).apply();
    }

    private void initLastSearchRecyclerView() {
        ArrayList<LastSearchDomain> lastSearchData = loadLastSearchData();
        Collections.reverse(lastSearchData);
        lastSearchAdapter = new LastSearchAdapter(this, lastSearchData);
        rvLastSearch.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvLastSearch.setAdapter(lastSearchAdapter);
    }

    private void getAllPopularHotels() {
        new PopularHotelApiCallAsyncTask(this, this).execute();
    }

    @Override
    public void onApiCallSuccess(List<Hotel> popularHotels) {
        if (popularHotels != null) {
            List<Hotel> firstFiveHotels = popularHotels.subList(0, Math.min(5, popularHotels.size()));
            mPopularHotelAdapter = new PopularHotelAdapter(this, firstFiveHotels);
            rvPopularHotel.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            rvPopularHotel.setAdapter(mPopularHotelAdapter);
        }
    }

    @Override
    public void onApiCallFailure(String errorMessage) {
        Log.e("API Error", errorMessage);
    }

    private void getHighRatingHotels() {
        new AllHotelApiCallAsyncTask(this).execute();
    }


    @Override
    public void onGetAllHotelsCompleted(List<Hotel> hotels) {
        if (hotels != null) {
            hotels.sort((hotel1, hotel2) -> Double.compare(hotel2.getRate(), hotel1.getRate()));
            List<Hotel> firstFiveHighRatingHotels = hotels.subList(0, Math.min(5, hotels.size()));
            highRatingHotelAdapter = new HighRatingHotelAdapter(this, firstFiveHighRatingHotels);
            rvHighRatingHotel.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            rvHighRatingHotel.setAdapter(highRatingHotelAdapter);
        }
    }

    @Override
    public void onGetAllHotelsFailure(String errorMessage) {
        Log.e("API Error", errorMessage);
    }
}
