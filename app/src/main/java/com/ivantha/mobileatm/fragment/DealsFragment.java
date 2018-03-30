package com.ivantha.mobileatm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.adapter.DealRecyclerAdapter;
import com.ivantha.mobileatm.model.Deal;

import java.util.ArrayList;
import java.util.List;

public class DealsFragment extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DealRecyclerAdapter dealRecyclerAdapter;

    private List<Deal> deals = new ArrayList<>();

    public DealsFragment() {
        // Required empty public constructor
    }

    public static DealsFragment newInstance() {
        DealsFragment fragment = new DealsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize GridLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.generateDefaultLayoutParams();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        recyclerView = view.findViewById(R.id.dealRecyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        Deal deal1 = new Deal();
        deal1.setTitle("Some A");
        deal1.setDescription("You will never get a discount like this");
        deal1.setPlace("CrazzyHut");
        deal1.setImageUrl("https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg");
        deals.add(deal1);

        Deal deal2 = new Deal();
        deal2.setTitle("Some A");
        deal2.setDescription("You will never get a discount like this");
        deal2.setPlace("CrazzyHut");
        deal2.setImageUrl("https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg");
        deals.add(deal2);

        Deal deal3 = new Deal();
        deal3.setTitle("Some A");
        deal3.setDescription("You will never get a discount like this");
        deal3.setPlace("CrazzyHut");
        deal3.setImageUrl("https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg");
        deals.add(deal3);

        Deal deal4 = new Deal();
        deal4.setTitle("Some A");
        deal4.setDescription("You will never get a discount like this");
        deal4.setPlace("CrazzyHut");
        deal4.setImageUrl("https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg");
        deals.add(deal4);

        Deal deal5 = new Deal();
        deal5.setTitle("Some A");
        deal5.setDescription("You will never get a discount like this");
        deal5.setPlace("CrazzyHut");
        deal5.setImageUrl("https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg");
        deals.add(deal5);

        Deal deal6 = new Deal();
        deal6.setTitle("Some A");
        deal6.setDescription("You will never get a discount like this");
        deal6.setPlace("CrazzyHut");
        deal6.setImageUrl("https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg");
        deals.add(deal6);

        dealRecyclerAdapter = new DealRecyclerAdapter(deals);
        recyclerView.setAdapter(dealRecyclerAdapter);

        return view;
    }
}
