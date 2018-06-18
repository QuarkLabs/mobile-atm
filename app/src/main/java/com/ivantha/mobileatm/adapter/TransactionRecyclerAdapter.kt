package com.ivantha.mobileatm.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.model.Transaction
import com.squareup.picasso.Picasso

class TransactionRecyclerAdapter(private val transactions: List<Transaction>, private var context: Context) : RecyclerView.Adapter<TransactionRecyclerAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_transaction, parent, false)

        return TransactionViewHolder(v)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        Picasso.with(context).load(transactions[position].profileImageUrl).fit().centerCrop().into(holder.initiatorImageView)
        holder.transactionAmountTextView.text = transactions[position].amount.toString()
        holder.initiatorNameTextView.text = transactions[position].initiatorName
        holder.transactionDateTextView.text = transactions[position].timestamp.toString()
        holder.transactionTitleTextView.text = transactions[position].title
        holder.transactionDescriptionTextView.text = transactions[position].description
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.transactionCardView)
        val initiatorImageView: ImageView = itemView.findViewById(R.id.initiatorImageView)
        val transactionAmountTextView: TextView = itemView.findViewById(R.id.transactionAmountTextView)
        val initiatorNameTextView: TextView = itemView.findViewById(R.id.initiatorNameTextView)
        val transactionDateTextView: TextView = itemView.findViewById(R.id.transactionDateTextView)
        val transactionTitleTextView: TextView = itemView.findViewById(R.id.transactionTitleTextView)
        val transactionDescriptionTextView: TextView = itemView.findViewById(R.id.transactionDescriptionTextView)

        init {
            cardView.setOnClickListener {
                // TODO: 4/22/18
            }
        }
    }
}
