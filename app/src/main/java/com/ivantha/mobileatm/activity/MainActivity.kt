package com.ivantha.mobileatm.activity

import android.content.DialogInterface
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
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
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
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_main)

        // Set toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Start new transaction
        startTransactionFab.setOnClickListener {
            val transaction = Transaction(Transaction.Intention.SEND, 1.0)
            transaction.title = "Test"
            transaction.description = "A random transaction"
            transaction.initiatorId = "MThDUb24tsgDIiOqy3rPvYITj9l2"
            transaction.initiatorName = "Upeksha Liyanage"

            val message = TransactionServices.transactionToJson(transaction)
            val multiFormatWriter = MultiFormatWriter()
            try {
                val bitMatrix: BitMatrix = multiFormatWriter.encode(message, BarcodeFormat.QR_CODE, 1000, 1000)
                val barcodeEncoder = BarcodeEncoder()
                val bitmap: Bitmap = barcodeEncoder.createBitmap(bitMatrix)

                val imageView = ImageView(baseContext)
                imageView.setImageBitmap(bitmap)

                val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                        .setMessage(message)
                        .setView(imageView)
                builder.create().show()
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }

        // Process incoming transaction
        processTransactionFab.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this@MainActivity)
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.setPrompt("Scan your transaction QR code")
            intentIntegrator.setBeepEnabled(true)
            intentIntegrator.setBarcodeImageEnabled(true)
            intentIntegrator.initiateScan()
        }

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

        // Set Home as default view
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment.newInstance()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        // Set Picasso debugging
        Picasso.with(baseContext).setIndicatorsEnabled(true)

        mainActivity = this@MainActivity
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

    /**
     * Handle action bar item clicks here.
     * The action bar will automatically handle clicks on the Home/Up button,
     * so long as you specify a parent activity in AndroidManifest.xml.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()

                val transaction: Transaction = TransactionServices.transactionFromJson(result.contents)
                transaction.receiverId = FirebaseAuth.getInstance().currentUser!!.uid

                val dialogClickListener = DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    when (i) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            transaction.confirmed = true
                            sendTransaction(transaction)
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            transaction.confirmed = false
                            sendTransaction(transaction)
                        }
                    }
                }

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Do you confirm this payment?")
                        .setMessage(transaction.getStatement())
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    /**
     * Update the UI related to user profile
     */
    private fun updateProfileUI(user: FirebaseUser?) {
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

    /**
     * Sign out
     */
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val myIntent = Intent(this@MainActivity, LoginActivity::class.java)
        this@MainActivity.startActivity(myIntent)
    }

    /**
     * Send a transaction to the server through POST
     */
    private fun sendTransaction(transaction: Transaction) {
        val params = JSONObject()
        params.put("type", transaction.intention)
        params.put("title", transaction.title)
        params.put("description", transaction.description)
        params.put("amount", transaction.amount)
        params.put("initiatorId", transaction.initiatorId)
        params.put("receiverId", transaction.receiverId)
        params.put("created", transaction.intention)

        val jsonObjReq = object : JsonObjectRequest(Method.POST, "https://us-central1-mobile-atm-10742.cloudfunctions.net/route/transactions", params,
                Response.Listener<JSONObject> { response ->
                    Log.d(TAG, "/post request OK! Response: $response")
                    Toast.makeText(this, "/post request OK! Response: $response", Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener { error ->
                    VolleyLog.e(TAG, "/post request fail! Error: ${error.message}")
                    Toast.makeText(this, "/post request fail! Error: ${error.message}", Toast.LENGTH_LONG).show()
                }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers.put("Content-Type", "application/json")
                return headers
            }
        }

        addToRequestQueue(jsonObjReq)
    }

    fun <T> addToRequestQueue(request: Request<T>, tag: String) {
        request.tag = if (TextUtils.isEmpty(tag)) TAG else tag
        requestQueue?.add(request)
    }

    fun <T> addToRequestQueue(request: Request<T>) {
        request.tag = TAG
        requestQueue?.add(request)
    }

    fun cancelPendingRequests(tag: Any) {
        if (requestQueue != null) {
            requestQueue!!.cancelAll(tag)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        @get:Synchronized
        var mainActivity: MainActivity? = null
            private set
    }
}
