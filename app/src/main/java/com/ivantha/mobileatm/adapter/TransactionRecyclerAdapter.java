package com.ivantha.mobileatm.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.model.Transaction;

import java.util.List;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionViewHolder>{
    private List<Transaction> transactions;

    public TransactionRecyclerAdapter() {

    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_transaction, parent, false);
        TransactionViewHolder transactionViewHolder = new TransactionViewHolder(v);

        return transactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        public TransactionViewHolder(View itemView) {
            super(itemView);
        }
    }
}
