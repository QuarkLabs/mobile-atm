package com.ivantha.mobileatm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.ivantha.mobileatm.R;

public class PaymentsFragment extends Fragment {

    private Spinner paymentsCategorySpinner;
    private Spinner paymentsPayeeSpinner;

    public PaymentsFragment() {
        // Required empty public constructor
    }

    public static PaymentsFragment newInstance() {
        PaymentsFragment fragment = new PaymentsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payments, container, false);

        // Payment Categories
        paymentsCategorySpinner = view.findViewById(R.id.paymentsCategorySpinner);
        ArrayAdapter<CharSequence> paymentsCategoryAdapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.payments_categories,
                android.R.layout.simple_spinner_item);
        paymentsCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentsCategorySpinner.setAdapter(paymentsCategoryAdapter);

        // Payment Payees
        paymentsPayeeSpinner = view.findViewById(R.id.paymentsPayeeSpinner);
        ArrayAdapter<CharSequence> paymentsPayeeAdapter = ArrayAdapter.createFromResource(
                view.getContext(),
                R.array.payment_payees,
                android.R.layout.simple_spinner_item);
        paymentsPayeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentsPayeeSpinner.setAdapter(paymentsPayeeAdapter);


        return view;
    }

}
