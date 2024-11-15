package com.example.apphotel.Searching.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.R;
import com.example.apphotel.Searching.Activity.SearchingResultsActivity;
import com.example.apphotel.Searching.Domain.LastSearchDomain;

import java.util.ArrayList;

public class LastSearchAdapter extends RecyclerView.Adapter<LastSearchAdapter.lastSearchHolder> {
    Context context;
    ArrayList<LastSearchDomain> arrLastSearchData;

    public LastSearchAdapter(Context context, ArrayList<LastSearchDomain> arrLastSearchData) {
        this.context = context;
        this.arrLastSearchData = arrLastSearchData;
    }

    public ArrayList<LastSearchDomain> getDataList() {
        return arrLastSearchData;
    }



    @NonNull
    @Override
    public lastSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_last_search, parent, false);
        return new lastSearchHolder(view);
    }




    @Override
    public void onBindViewHolder(@NonNull lastSearchHolder holder, int position) {
        LastSearchDomain item = arrLastSearchData.get(position);

        holder.tvName.setText(item.getName());
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchingResultsActivity.class);
                intent.putExtra("SEARCH_QUERY", item.getName());
                context.startActivity(intent);
            }
        });
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrLastSearchData.size();
    }

    public void setData(ArrayList<LastSearchDomain> newData) {
        arrLastSearchData.clear();
        arrLastSearchData.addAll(newData);
    }

    public void updateData(ArrayList<LastSearchDomain> newData) {
        this.arrLastSearchData = newData;
    }

    private void removeItem(int position) {
        if (position >= 0 && position < arrLastSearchData.size()) {
            arrLastSearchData.remove(position);
            notifyItemRemoved(position);

            // Notify the adapter that the data set has changed
            notifyItemRangeChanged(position, arrLastSearchData.size());
        }
    }

    public class lastSearchHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageButton removeBtn;

        public lastSearchHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.item_tv_last_search);
            removeBtn = itemView.findViewById(R.id.searching_remove_button);
        }
    }
}
