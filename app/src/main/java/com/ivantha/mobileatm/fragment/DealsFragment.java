package com.ivantha.mobileatm.fragment;

import android.content.Context;
import android.net.Uri;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DealsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DealsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DealsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private DealRecyclerAdapter dealRecyclerAdapter;

    private List<Deal> deals = new ArrayList<>();

    public DealsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DealsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DealsFragment newInstance(String param1, String param2) {
        DealsFragment fragment = new DealsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // Initialize GridLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.generateDefaultLayoutParams();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        recyclerView = view.findViewById(R.id.dealsRecyclerView);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDealsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDealsFragmentInteraction(Uri uri);
    }
}
