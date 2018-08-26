package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.adapter.TransactionRecyclerAdapter
import com.ivantha.mobileatm.common.Session

class HistoryFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var transactionRecyclerAdapter: TransactionRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize GridLayoutManager
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager!!.generateDefaultLayoutParams()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.transactionRecyclerView)
        recyclerView!!.layoutManager = linearLayoutManager
        recyclerView!!.setHasFixedSize(true)

        transactionRecyclerAdapter = TransactionRecyclerAdapter(Session.transactions.values.toList(), this.context!!)
        recyclerView!!.adapter = transactionRecyclerAdapter

        return view
    }

    companion object {

        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }
}
