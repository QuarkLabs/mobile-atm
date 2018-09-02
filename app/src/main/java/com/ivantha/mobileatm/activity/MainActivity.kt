package com.ivantha.mobileatm.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
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
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.zxing.integration.android.IntentIntegrator
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.fragment.*
import com.ivantha.mobileatm.model.Transaction
import com.ivantha.mobileatm.model.User
import com.ivantha.mobileatm.service.TransactionServices
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var currentUser: User? = null


    private val requestQueue: RequestQueue? = null
        get() {
            if (field == null) {
                return Volley.newRequestQueue(applicationContext)
            }
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentUser= getIntent().getSerializableExtra("currentUser") as User?;
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        setContentView(R.layout.activity_main)

        //set application context
        MainActivity.context = getApplicationContext()

        // Set toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

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
        //Pass login data
        val bundle = Bundle()
        println("*******************"+currentUser)
        bundle.putSerializable("currentUser", currentUser )
        fragment?.setArguments(bundle)

        transaction.replace(R.id.container, fragment)
        transaction.commit()

        // Set Picasso debugging
        Picasso.with(baseContext).setIndicatorsEnabled(true)

        //updateUI
        updateProfileUI()

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
            R.id.nav_payment -> fragment = PaymentFragment.newInstance()
            R.id.nav_utility_bills -> fragment = BillsFragment.newInstance()
            R.id.nav_deals -> fragment = DealsFragment.newInstance()
            R.id.nav_history -> fragment = HistoryFragment.newInstance()
            R.id.nav_account -> fragment = AccountFragment.newInstance()
            R.id.nav_settings -> fragment = SettingsFragment.newInstance()
        }

        //Pass login data
        val bundle:Bundle = Bundle()
        bundle.putSerializable("currentUser", currentUser )
        fragment?.setArguments(bundle)
        println("******************* main got?"+currentUser)
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
    private fun updateProfileUI() {// Points to 'profiles'
        var profilesRef: StorageReference? = FirebaseStorage.getInstance().getReference("/profiles")

        val headerView: View = navigationView.getHeaderView(0)

        Toast.makeText(this@MainActivity, currentUser.toString(), Toast.LENGTH_SHORT).show()
        if (currentUser != null) {
            if (currentUser!!.firstName != null) {
                println("ui"+currentUser!!.firstName)
                headerView.navHeaderNameTextView.text = currentUser!!.firstName
            }
            if (currentUser!!.email != null) {
                headerView.navHeaderEmailTextView.text = currentUser!!.email
            }

            var profilePicture = profilesRef!!.child(FirebaseAuth.getInstance().currentUser!!.uid)

            profilePicture.downloadUrl.addOnSuccessListener { uri ->
                Picasso.with(context).load(uri.toString()).fit().centerCrop().into( headerView.navHeaderProfileImageView);
            }.addOnFailureListener {
                // TODO : Handle error
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

    fun showFCM(title: String, body: String) {
        MaterialDialog.Builder(MainActivity@ this)
                .title(title)
                .content(body)
                .positiveText("OK")
                .show()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        @get:Synchronized
        var mainActivity: MainActivity? = null
            private set

        //Application Context - to use in fragments
        @JvmField
        var context: Context? = null

        // Not really needed since we can access the variable directly.
        @JvmStatic
        fun getAppContext(): Context? {
            return context
        }
    }

}
