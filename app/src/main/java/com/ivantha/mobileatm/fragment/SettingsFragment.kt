package com.ivantha.mobileatm.fragment

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

import com.ivantha.mobileatm.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.settings)
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

}
