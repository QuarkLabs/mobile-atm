package com.ivantha.mobileatm.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.model.Transaction

class TransactionRecyclerAdapter : RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionViewHolder>() {
    private val transactions: List<Transaction>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_transaction, parent, false)

        return TransactionViewHolder(v)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return transactions!!.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val cardView: CardView? = null
    }
}
