package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ivantha.mobileatm.R
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_bills.*


class BillsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shake = AnimationUtils.loadAnimation(context, R.anim.shakeanimation)
        utilityFragmentWaterButton.animation = shake
        utilityFragmentElectricityButton.animation = shake
        utilityFragmentPhoneButton.animation = shake
        utilityFragmentInternetButton.animation = shake
        utilityFragmentLeasingButton.animation = shake
        utilityFragmentInsuranceButton.animation = shake
        utilityFragmentGasButton.animation = shake
        utilityFragmentFuelButton.animation = shake
        utilityFragmentEducationButton.animation = shake
        utilityFragmentMedicalButton.animation = shake
    }

    companion object {

        fun newInstance(): BillsFragment {
            return BillsFragment()
        }
    }

}
