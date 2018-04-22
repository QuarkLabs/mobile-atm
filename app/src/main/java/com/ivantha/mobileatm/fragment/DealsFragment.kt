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
import com.ivantha.mobileatm.model.Deal
import java.util.*

class DealsFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var dealRecyclerAdapter: DealRecyclerAdapter? = null

    private val deals = ArrayList<Deal>()

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

        val deal1 = Deal()
        deal1.title = "Some A"
        deal1.description = "You will never get a discount like this"
        deal1.place = "CrazzyHut"
        deal1.imageUrl = "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"
        deals.add(deal1)

        val deal2 = Deal()
        deal2.title = "Some A"
        deal2.description = "You will never get a discount like this"
        deal2.place = "CrazzyHut"
        deal2.imageUrl = "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"
        deals.add(deal2)

        val deal3 = Deal()
        deal3.title = "Some A"
        deal3.description = "You will never get a discount like this"
        deal3.place = "CrazzyHut"
        deal3.imageUrl = "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"
        deals.add(deal3)

        val deal4 = Deal()
        deal4.title = "Some A"
        deal4.description = "You will never get a discount like this"
        deal4.place = "CrazzyHut"
        deal4.imageUrl = "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"
        deals.add(deal4)

        val deal5 = Deal()
        deal5.title = "Some A"
        deal5.description = "You will never get a discount like this"
        deal5.place = "CrazzyHut"
        deal5.imageUrl = "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"
        deals.add(deal5)

        val deal6 = Deal()
        deal6.title = "Some A"
        deal6.description = "You will never get a discount like this"
        deal6.place = "CrazzyHut"
        deal6.imageUrl = "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"
        deals.add(deal6)

        dealRecyclerAdapter = DealRecyclerAdapter(deals, this.context!!)
        recyclerView!!.adapter = dealRecyclerAdapter

        return view
    }

    companion object {

        fun newInstance(): DealsFragment {
            return DealsFragment()
        }
    }
}
