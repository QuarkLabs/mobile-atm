package com.ivantha.mobileatm.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Session.currentUser != null) {
            fragmentAccountFirstNameEditText.setText(Session.currentUser!!.firstName)
            fragmentAccountLastNameEditText.setText(Session.currentUser!!.lastName)
            fragmentAccountAccountBalanceEditText.setText(Session.currentUser!!.account!!.balance.toString())
            fragmentAccountSpendingLimitEditText.setText(Session.currentUser!!.account!!.spendingLimit.toString())
            fragmentAccountEnableSpendingLimitSwitch.isChecked = Session.currentUser!!.account!!.spendingLimitEnable
        }

        fragmentAccountSaveButton.setOnClickListener {
            if (Session.currentUser != null) {
                Session.currentUser!!.firstName = fragmentAccountFirstNameEditText.text.toString()
                Session.currentUser!!.lastName = fragmentAccountLastNameEditText.text.toString()
                Session.currentUser!!.account!!.balance = fragmentAccountAccountBalanceEditText.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimit = fragmentAccountSpendingLimitEditText.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimitEnable = fragmentAccountEnableSpendingLimitSwitch.isChecked

                Session.updateUser()
                Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {

        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }
}
