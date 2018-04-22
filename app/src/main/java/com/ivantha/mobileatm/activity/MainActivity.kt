package com.ivantha.mobileatm.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session
import com.ivantha.mobileatm.fragment.*
import com.ivantha.mobileatm.model.Account
import com.ivantha.mobileatm.model.Settings
import com.ivantha.mobileatm.model.User
import com.squareup.picasso.Picasso
import org.jetbrains.annotations.Contract

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ////////////////////////////////////////////////////////////////////////////////////////////
        // Emulating the login
        val user = User()
        user.firstName = "Oshan"
        user.lastName = "Mudannayake"
        user.email = "oshan.ivantha@gmail.com"
        user.password = "oshan1234"
        user.seed = "VKN9VNOZUFMWMMIUVZLVXUTFPWRGQQBNGEYWBHUYQMXNKPDDFAHVQJKCQRHUYHGRBLCIHDWHUGK99FHCI"

        val account = Account()
        account.setSpendingLimit(4570.0)
        user.account = account

        val settings = Settings()
        user.settings = settings

        Session.currentUser = user
        ////////////////////////////////////////////////////////////////////////////////////////////

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@MainActivity)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.setPrompt("Scan your transaction QR code")
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setBarcodeImageEnabled(true)
            intentIntegrator.initiateScan()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        mainActivity = this@MainActivity

        // Set Home as default view
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment.newInstance()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        // Set Picasso debugging
        Picasso.with(baseContext).setIndicatorsEnabled(true)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_help) {
            return true
        } else if (id == R.id.action_report) {
            // TMP: Open Login Page //
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        var fragment: Fragment? = null

        if (id == R.id.nav_home) {
            fragment = HomeFragment.newInstance()
        } else if (id == R.id.nav_payments) {
            fragment = PaymentsFragment.newInstance()
        } else if (id == R.id.nav_recharge) {
            fragment = RechargeFragment.newInstance()
        } else if (id == R.id.nav_deals) {
            fragment = DealsFragment.newInstance()
        } else if (id == R.id.nav_history) {
            fragment = HistoryFragment.newInstance()
        } else if (id == R.id.nav_account) {
            fragment = AccountFragment.newInstance()
        } else if (id == R.id.nav_settings) {
            fragment = SettingsFragment.newInstance()
        }
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    companion object {

        @get:Contract(pure = true)
        var mainActivity: MainActivity? = null
            private set
    }
}
