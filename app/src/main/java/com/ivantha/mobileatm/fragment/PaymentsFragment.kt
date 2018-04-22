package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.ivantha.mobileatm.R

class PaymentsFragment : Fragment() {

    private var paymentsCategorySpinner: Spinner? = null
    private var paymentsPayeeSpinner: Spinner? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payments, container, false)

        // Payment Categories
        paymentsCategorySpinner = view.findViewById(R.id.paymentsCategorySpinner)
        val paymentsCategoryAdapter = ArrayAdapter.createFromResource(
                view.context,
                R.array.payments_categories,
                android.R.layout.simple_spinner_item)
        paymentsCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentsCategorySpinner!!.adapter = paymentsCategoryAdapter

        // Payment Payees
        paymentsPayeeSpinner = view.findViewById(R.id.paymentsPayeeSpinner)
        val paymentsPayeeAdapter = ArrayAdapter.createFromResource(
                view.context,
                R.array.payment_payees,
                android.R.layout.simple_spinner_item)
        paymentsPayeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        paymentsPayeeSpinner!!.adapter = paymentsPayeeAdapter


        return view
    }

    companion object {

        fun newInstance(): PaymentsFragment {
            return PaymentsFragment()
        }
    }

}
