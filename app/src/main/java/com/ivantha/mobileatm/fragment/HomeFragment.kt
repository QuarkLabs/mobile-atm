package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session

class HomeFragment : Fragment() {

    private var spendingLimitTextView: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        spendingLimitTextView = view.findViewById(R.id.spendingLimitTextView)
        spendingLimitTextView!!.text = Session.currentUser!!.account!!.spendingLimit.toString()

        // Inflate the layout for this fragment
        return view
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

}// Required empty public constructor
