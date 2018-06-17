package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.adapter.DealRecyclerAdapter
import com.ivantha.mobileatm.common.Session

class DealsFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var dealRecyclerAdapter: DealRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize GridLayoutManager
        gridLayoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
        gridLayoutManager!!.generateDefaultLayoutParams()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_deals, container, false)

        recyclerView = view.findViewById(R.id.dealRecyclerView)
        recyclerView!!.layoutManager = gridLayoutManager
        recyclerView!!.setHasFixedSize(true)

        dealRecyclerAdapter = DealRecyclerAdapter(Session.deals.values.toList(), this.context!!)
        recyclerView!!.adapter = dealRecyclerAdapter

        return view
    }

    companion object {

        fun newInstance(): DealsFragment {
            return DealsFragment()
        }
    }
}
