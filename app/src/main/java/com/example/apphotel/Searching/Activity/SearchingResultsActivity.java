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
    ResultFilterAdapter resultFilterAdapter;
    ResultItemAdapter resultItemAdapter;
    RecyclerView rvResultFilter, rvResultItem;
    androidx.appcompat.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_results_layout);

        rvResultFilter = findViewById(R.id.searching_rv_search_result_filter);
        innitResultFilterRecyclerView();

        rvResultItem = findViewById(R.id.searching_rv_search_result_items);
        resultItemAdapter = new ResultItemAdapter(this, new ArrayList<>());

        searchView = findViewById(R.id.searching_ed_result_search_view);


        Intent intent = getIntent();
        String searchQuery = intent.getStringExtra("SEARCH_QUERY");
        searchView.setQuery(searchQuery, false);
        getSearchHotels(searchQuery);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                getSearchHotels(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchHotels(newText);
                return true;
            }
        });

        ImageButton resultBackBtn = findViewById(R.id.searching_result_back_button);
        resultBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchingResultsActivity.this, SearchingActivity.class);
                startActivity(intent);
            }
        });
        // End onCreate()
    }


    private void getSearchHotels(String searchQuery) {
        new SearchHotelApiCallAsyncTask(this, this).execute(searchQuery);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onApiCallSuccess(List<Hotel> hotels) {
        resultItemAdapter = new ResultItemAdapter(this, hotels);
        resultItemAdapter.notifyDataSetChanged();
        rvResultItem.setLayoutManager(new GridLayoutManager(this, 2));
        rvResultItem.setAdapter(resultItemAdapter);
    }

    @Override
    public void onApiCallFailure(String errorMessage) {
        Toast.makeText(this, "Api call failed", Toast.LENGTH_SHORT).show();
        Log.e("API Error", errorMessage);
    }


    private void innitResultFilterRecyclerView() {
        ArrayList<ResultFilterDomain> arrResultFilterData = new ArrayList<>();
        //  Soft by reviewQuantity
        arrResultFilterData.add(new ResultFilterDomain("Most Popular"));
        //  Soft by price
        arrResultFilterData.add(new ResultFilterDomain("Cheapest"));
        //  Soft by rate
        arrResultFilterData.add(new ResultFilterDomain("High Rating"));

        resultFilterAdapter = new ResultFilterAdapter(arrResultFilterData);

        rvResultFilter.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvResultFilter.setAdapter(resultFilterAdapter);
    }
}


