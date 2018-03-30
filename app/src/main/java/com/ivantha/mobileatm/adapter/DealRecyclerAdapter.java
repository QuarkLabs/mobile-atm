package com.ivantha.mobileatm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.activity.MainActivity;
import com.ivantha.mobileatm.model.Deal;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DealRecyclerAdapter extends RecyclerView.Adapter<DealRecyclerAdapter.DealViewHolder>{
    private List<Deal> deals;

    public DealRecyclerAdapter(List<Deal> deals) {
        this.deals = deals;
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
        Picasso.get().load(deals.get(position).getImageUrl()).fit().centerCrop().into(holder.dealImageView);
        holder.dealTitleTextView.setText(deals.get(position).getTitle());
        holder.dealDescriptionTextView.setText(deals.get(position).getDescription());
        holder.dealPlaceTextView.setText(deals.get(position).getPlace());
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    class DealViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private ImageView dealImageView;
        private TextView dealTitleTextView;
        private TextView dealDescriptionTextView;
        private TextView dealPlaceTextView;

        public DealViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.dealCardView);
            dealImageView = itemView.findViewById(R.id.dealImageView);
            dealTitleTextView = itemView.findViewById(R.id.dealTitleTextView);
            dealDescriptionTextView = itemView.findViewById(R.id.dealDescriptionTextView);
            dealPlaceTextView = itemView.findViewById(R.id.dealPlaceTextView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.getMainActivity(), "Clicked", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
