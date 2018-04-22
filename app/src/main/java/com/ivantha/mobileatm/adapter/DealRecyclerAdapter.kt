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
import com.ivantha.mobileatm.model.Deal
import com.squareup.picasso.Picasso

class DealRecyclerAdapter(private val deals: List<Deal>, private var context: Context) : RecyclerView.Adapter<DealRecyclerAdapter.DealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DealViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_deal, parent, false)

        return DealViewHolder(v)
    }

    override fun onBindViewHolder(holder: DealViewHolder, position: Int) {
        Picasso.with(context).load(deals[position].imageUrl).fit().centerCrop().into(holder.dealImageView)
        holder.dealTitleTextView.text = deals[position].title
        holder.dealDescriptionTextView.text = deals[position].description
        holder.dealPlaceTextView.text = deals[position].place
    }

    override fun getItemCount(): Int {
        return deals.size
    }

    inner class DealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.dealCardView)
        val dealImageView: ImageView = itemView.findViewById(R.id.dealImageView)
        val dealTitleTextView: TextView = itemView.findViewById(R.id.dealTitleTextView)
        val dealDescriptionTextView: TextView = itemView.findViewById(R.id.dealDescriptionTextView)
        val dealPlaceTextView: TextView = itemView.findViewById(R.id.dealPlaceTextView)

        init {
            cardView.setOnClickListener {
                // TODO: 4/22/18
            }
        }
    }
}
