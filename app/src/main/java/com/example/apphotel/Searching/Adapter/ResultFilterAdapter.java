package com.example.apphotel.Searching.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apphotel.R;
import com.example.apphotel.Searching.Domain.ResultFilterDomain;

import java.util.ArrayList;

public class ResultFilterAdapter extends RecyclerView.Adapter<ResultFilterAdapter.resultFilterHolder> {
    ArrayList<ResultFilterDomain> arrResultFilterData;

    public ResultFilterAdapter(ArrayList<ResultFilterDomain> arrResultFilterData) {
        this.arrResultFilterData = arrResultFilterData;
    }

    @NonNull
    @Override
    public ResultFilterAdapter.resultFilterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searching_item_search_result_filter, parent, false);
        return new resultFilterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull resultFilterHolder holder, int position) {
    holder.tvName.setText(arrResultFilterData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrResultFilterData.size();
    }

    public class resultFilterHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public resultFilterHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_tv_search_result_filter);
        }
    }
}
