package com.ivantha.mobileatm.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.fragment.*
import com.ivantha.mobileatm.model.Transaction
import com.ivantha.mobileatm.service.TransactionServices
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.annotations.Contract


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        transactionFab.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@MainActivity)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.setPrompt("Scan your transaction QR code")
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setBarcodeImageEnabled(true)
            intentIntegrator.initiateScan()

        }

        fakeFab.setOnClickListener({
            var transaction = Transaction(Transaction.Intention.REQUEST, 1.0)
            transaction.title = "Test"
            transaction.description = "A random transaction"

            var message = TransactionServices.transactionToJson(transaction)
            var multiFormatWriter = MultiFormatWriter()
            try {
                var bitMatrix: BitMatrix = multiFormatWriter.encode(message, BarcodeFormat.QR_CODE, 1000, 1000)
                var barcodeEncoder = BarcodeEncoder()
                var bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

                var imageView = ImageView(baseContext)
                imageView.setImageBitmap(bitmap)

                var builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        .setMessage(message)
                        .setView(imageView);
                builder.create().show()
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        })

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

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

        if (id == R.id.nav_sign_out) {
            signOut()
            return true
        }

        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        var fragment: Fragment? = null

        when (id) {
            R.id.nav_home -> fragment = HomeFragment.newInstance()
            R.id.nav_utility_bills -> fragment = BillsFragment.newInstance()
            R.id.nav_deals -> fragment = DealsFragment.newInstance()
            R.id.nav_history -> fragment = HistoryFragment.newInstance()
            R.id.nav_account -> fragment = AccountFragment.newInstance()
            R.id.nav_settings -> fragment = SettingsFragment.newInstance()
        }
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    // Get the results
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

                var transaction: Transaction = TransactionServices.transactionFromJson(result.contents)
                when (transaction.intention) {
                    Transaction.Intention.SEND -> {
                        // Receive cash sent by the other party
                        receiveCash(transaction)
                    }
                    Transaction.Intention.REQUEST -> {
                        // Send cash requested by the other party
                        sendCash(transaction)
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            if (user.displayName != null) {
                navHeaderNameTextView.text = user.displayName
            }
            if (user.email != null) {
                navHeaderEmailTextView.text = user.email
            }
            if (user.photoUrl != null) {
                Picasso.with(this@MainActivity).load(user.photoUrl).fit().centerCrop().into(navHeaderProfileImageView)
            }
        }
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut();
        val myIntent = Intent(this@MainActivity, LoginActivity::class.java)
        this@MainActivity.startActivity(myIntent)
    }

    private fun receiveCash(transaction: Transaction){

    }

    private fun sendCash(transaction: Transaction){

    }

    companion object {

        @get:Contract(pure = true)
        var mainActivity: MainActivity? = null
            private set
    }
}
