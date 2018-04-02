package com.ivantha.mobileatm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivantha.mobileatm.R;

public class AccountFragment extends PreferenceFragmentCompat {

    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.account);
    }

}
