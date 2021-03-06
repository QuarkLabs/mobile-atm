package com.ivantha.mobileatm.common

import android.graphics.Color
import com.google.gson.Gson
import com.ivantha.mobileatm.model.Deal
import com.ivantha.mobileatm.model.Transaction

/**
 * Contains the global variable of the current session of the app
 */
object Session {

    // JSON parser
    var gson: Gson? = null

    // Deals
    var deals: HashMap<String, Deal> = HashMap()
    val dealColors: ArrayList<Int> = ArrayList()

    // Transactions
    var transactions: HashMap<String, Transaction> = HashMap()

    /**
     * Initialize the session variable and functions
     */
    init {
        // Add deal colors
        dealColors.add(Color.parseColor("#e53935"))
        dealColors.add(Color.parseColor("#1E88E5"))
        dealColors.add(Color.parseColor("#43A047"))
        dealColors.add(Color.parseColor("#546E7A"))
        dealColors.add(Color.parseColor("#8E24AA"))
        dealColors.add(Color.parseColor("#00ACC1"))
        dealColors.add(Color.parseColor("#F4511E"))
        dealColors.add(Color.parseColor("#3949AB"))
    }

}
