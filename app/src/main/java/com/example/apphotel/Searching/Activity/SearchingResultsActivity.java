package com.example.apphotel.Searching.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.R;
import com.example.apphotel.Searching.Adapter.ResultFilterAdapter;
import com.example.apphotel.Searching.Adapter.ResultItemAdapter;
import com.example.apphotel.Searching.AsyncTask.SearchHotelApiCallAsyncTask;
import com.example.apphotel.Searching.Domain.Hotel;
import com.example.apphotel.Searching.Domain.ResultFilterDomain;

import java.util.ArrayList;
import java.util.List;

public class SearchingResultsActivity extends AppCompatActivity implements SearchHotelApiCallAsyncTask.ApiCallListener {

    private ResultItemAdapter resultItemAdapter;
    private RecyclerView rvResultItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_results_layout);

        // Initialize views
        rvResultItem = findViewById(R.id.searching_rv_search_result_items);
        searchView = findViewById(R.id.searching_ed_result_search_view);

        resultItemAdapter = new ResultItemAdapter(this, new ArrayList<>());
        rvResultItem.setLayoutManager(new GridLayoutManager(this, 2));
        rvResultItem.setAdapter(resultItemAdapter);

        // Handle search query from Intent
        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");
        if (searchQuery != null) {
            searchView.setQuery(searchQuery, false);
            getSearchHotels(searchQuery);
        }

        // Handle search input
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchHotels(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 2) {
                    getSearchHotels(newText);
                }
                return true;
            }
        });

        // Back button
        ImageButton resultBackBtn = findViewById(R.id.searching_result_back_button);
        resultBackBtn.setOnClickListener(v -> {
            Intent backIntent = new Intent(SearchingResultsActivity.this, SearchingActivity.class);
            startActivity(backIntent);
        });
    }

    private void getSearchHotels(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            Toast.makeText(this, "Search query cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        new SearchHotelApiCallAsyncTask(this).execute(searchQuery);
    }

    @Override
    public void onApiCallSuccess(List<Hotel> hotels) {
        if (hotels != null && !hotels.isEmpty()) {
            updateSearchResults(hotels);
        } else {
            Toast.makeText(this, "No results found.", Toast.LENGTH_SHORT).show();
            clearSearchResults();
        }
    }

    @Override
    public void onApiCallFailure(String errorMessage) {
        Toast.makeText(this, "Error fetching search results: " + errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("SearchHotelsError", errorMessage);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateSearchResults(List<Hotel> hotels) {
        resultItemAdapter.updateData(hotels);
    }

    private void clearSearchResults() {
        resultItemAdapter.updateData(new ArrayList<>());
    }
}

