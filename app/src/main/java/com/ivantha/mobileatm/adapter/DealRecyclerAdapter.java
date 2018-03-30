package com.ivantha.mobileatm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.model.Deal;

import java.util.List;

public class DealRecyclerAdapter extends RecyclerView.Adapter<DealRecyclerAdapter.DealViewHolder>{
    private List<Deal> deals;

    public DealRecyclerAdapter() {

    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_deal, parent, false);
        DealViewHolder dealViewHolder = new DealViewHolder(v);

        return dealViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    class DealViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public DealViewHolder(View itemView) {
            super(itemView);
        }
    }
}
