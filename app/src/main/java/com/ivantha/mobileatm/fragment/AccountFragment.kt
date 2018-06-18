package com.ivantha.mobileatm.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session

class AccountFragment : Fragment() {
    var txt_acc_balance: TextView? = null
    var txt_acc_unverfiedBalance: TextView? = null
    var txt_acc_spendingLimit: TextView? = null
    var swt_acc_spendingLimitEnable: Switch? = null
    var txt_user_firstName: TextView? = null
    var txt_user_lastName: TextView? = null
    var btn_save: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        txt_acc_balance = view.findViewById(R.id.txt_acc_balance)
        txt_acc_unverfiedBalance = view.findViewById(R.id.txt_acc_unverfiedBalance)
        txt_acc_spendingLimit = view.findViewById(R.id.txt_acc_spendingLimit)
        swt_acc_spendingLimitEnable = view.findViewById(R.id.swt_acc_spendingLimitEnable)
        txt_user_firstName = view.findViewById(R.id.txt_user_firstName)
        txt_user_lastName = view.findViewById(R.id.txt_user_lastName)

        if (Session.currentUser != null) {
            txt_acc_balance!!.text = Session.currentUser!!.account!!.balance.toString()
            txt_acc_unverfiedBalance!!.text = Session.currentUser!!.account!!.balance.toString()
            txt_acc_spendingLimit!!.text = Session.currentUser!!.account!!.spendingLimit.toString()
            swt_acc_spendingLimitEnable!!.isChecked = Session.currentUser!!.account!!.spendingLimitEnable
            txt_user_firstName!!.text = Session.currentUser!!.firstName
            txt_user_lastName!!.text = Session.currentUser!!.lastName
        }

        val btn_save = view.findViewById(R.id.btn_save) as Button

        btn_save.setOnClickListener {
            if (Session.currentUser != null) {
                Session.currentUser!!.account!!.balance = txt_acc_balance!!.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimit = txt_acc_spendingLimit!!.text.toString().toDouble()
                Session.currentUser!!.account!!.spendingLimitEnable = swt_acc_spendingLimitEnable!!.isChecked
                Session.currentUser!!.firstName = txt_user_firstName!!.text.toString()
                Session.currentUser!!.lastName = txt_user_lastName!!.text.toString()
                Session.updateUser()
                Toast.makeText(activity, "Updated", Toast.LENGTH_LONG).show()

            }
        }
        return view
    }

    companion object {

        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }
}
