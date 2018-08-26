package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

//        spendingLimitTextView!!.text = Session.currentUser!!.account!!.spendingLimit.toString()

        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        super.onResume()

        spendingLimitTextView!!.text = Session.currentUser!!.account!!.spendingLimit.toString()
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

}