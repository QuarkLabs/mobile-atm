package com.ivantha.mobileatm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.common.Session;

public class HomeFragment extends Fragment {

    private TextView spendingLimitTextView;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        spendingLimitTextView = view.findViewById(R.id.spendingLimitTextView);
        spendingLimitTextView.setText(Session.getCurrentUser().getAccount().getSpendingLimit().toString());

        // Inflate the layout for this fragment
        return view;
    }

}
