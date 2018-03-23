package com.ivantha.mobileatm.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.ivantha.mobileatm.fragment.AccountFragment;
import com.ivantha.mobileatm.fragment.PayFragment;
import com.ivantha.mobileatm.R;
import com.ivantha.mobileatm.fragment.RechargeFragment;

public class MainActivity extends AppCompatActivity implements PayFragment.OnFragmentInteractionListener, AccountFragment.OnFragmentInteractionListener,
        RechargeFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_recharge:
                    fragment = RechargeFragment.newInstance("p1", "p2");
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_pay:
                    fragment = PayFragment.newInstance("p1", "p2");
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                    return true;
                case R.id.navigation_account:
                    fragment = AccountFragment.newInstance("p1", "p2");
                    transaction.replace(R.id.container, fragment);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onPayFragmentInteraction(Uri uri) {

    }

    @Override
    public void onAccountFragmentInteraction(Uri uri) {

    }

    @Override
    public void onRechargeFragmentInteraction(Uri uri) {

    }
}
