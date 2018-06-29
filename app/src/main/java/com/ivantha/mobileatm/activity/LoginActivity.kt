package com.ivantha.mobileatm.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session
import com.ivantha.mobileatm.common.Test
import com.ivantha.mobileatm.model.Deal
import com.ivantha.mobileatm.model.Transaction
import com.ivantha.mobileatm.model.User
import kotlinx.android.synthetic.main.activity_login.*
import org.joda.time.DateTime
import org.joda.time.format.ISODateTimeFormat


class LoginActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        initGson()

        // Navigate to MainActivity if the user is already logged in
        if (firebaseAuth!!.currentUser != null) {
            Toast.makeText(applicationContext, "Already logged in", Toast.LENGTH_SHORT).show()

            showMainActivity()
        } else {
            setContentView(R.layout.activity_login)
        }
    }

    fun onClickLoginActivitySignInButton(view: View) {
        var email = loginActivityEmailEditText.text.toString()
        var password = loginActivityPasswordEditText.text.toString()

        if (email != null && password != null) {
            performLoginOrAccountCreation(email, password)
        }
    }

    private fun performLoginOrAccountCreation(email: String, password: String) {
        firebaseAuth!!.fetchSignInMethodsForEmail(email).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val result = task.result

                if (result != null && result.signInMethods != null && result.signInMethods!!.size > 0) {
                    performLogin(email, password)
                } else {
                    registerAccount(email, password)
                }
            } else {
                Toast.makeText(this@LoginActivity, "There is a problem, please try again later", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Login and navigate to MainActivity
     */
    private fun performLogin(email: String, password: String) {
        firebaseAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                showMainActivity()
            } else {
                Toast.makeText(this@LoginActivity, "Authentication failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Create a new login account in Firebase
     */
    private fun registerAccount(email: String, password: String) {
        firebaseAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@LoginActivity, "Account created", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this@LoginActivity, "Account registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Navigate to MainActivity
     */
    private fun showMainActivity() {
        Test.addTestData()
        initCurrentUser()
        initDeals()
        initTransactions()
        subscribeToMessages()

        val handler = Handler()
        val runnableCode = object : Runnable {
            override fun run() {
                if (Session.currentUser == null) {
                    println("Waiting for currentUser to be updated....")
                    handler.postDelayed(this, 200)
                } else {
                    val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    this@LoginActivity.startActivity(myIntent)
                }
            }
        }
        handler.post(runnableCode)
    }

    /**
     * Initialize current user
     */
    private fun initCurrentUser() {
        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Session.currentUser = dataSnapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Failed to read value")
            }
        })
    }

    /**
     * Initialize Gson
     */
    private fun initGson() {
        Session.gson = GsonBuilder()
                .registerTypeAdapter(DateTime::class.java, JsonSerializer<DateTime> { json, _, _ ->
                    JsonPrimitive(ISODateTimeFormat.dateTime().print(json))
                })
                .registerTypeAdapter(DateTime::class.java, JsonDeserializer { json, _, _ ->
                    ISODateTimeFormat.dateTime().parseDateTime(json.asString)
                })
                .create()
    }

    /**
     * Initialize deals
     */
    private fun initDeals() {
        FirebaseDatabase.getInstance().reference.child("deals").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                TODO("Failed to read value")
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Session.deals[dataSnapshot.key!!] = dataSnapshot.getValue<Deal>(Deal::class.java)!!
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Session.deals[dataSnapshot.key!!] = dataSnapshot.getValue<Deal>(Deal::class.java)!!
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Session.deals[dataSnapshot.key!!] = dataSnapshot.getValue<Deal>(Deal::class.java)!!
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Session.deals.remove(dataSnapshot.key)
            }

        })
    }

    /**
     * Initialize transactions
     */
    private fun initTransactions() {
        FirebaseDatabase.getInstance().reference.child("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("log").addChildEventListener(object : ChildEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                TODO("Failed to read value")
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Session.transactions[dataSnapshot.key!!] = dataSnapshot.getValue<Transaction>(Transaction::class.java)!!
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Session.transactions[dataSnapshot.key!!] = dataSnapshot.getValue<Transaction>(Transaction::class.java)!!
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                Session.transactions[dataSnapshot.key!!] = dataSnapshot.getValue<Transaction>(Transaction::class.java)!!
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                Session.transactions.remove(dataSnapshot.key)
            }

        })
    }

    /**
     * Subscribe to Firebase Cloud Messages
     */
    private fun subscribeToMessages() {
        FirebaseMessaging.getInstance().subscribeToTopic(FirebaseAuth.getInstance()!!.currentUser!!.uid)
                .addOnCompleteListener { task ->
                    var msg = "Successfully subscribed to FCM"
                    if (!task.isSuccessful) {
                        msg = "FCM subscribe unsuccessful"
                    }
                    Toast.makeText(this@LoginActivity, msg, Toast.LENGTH_SHORT).show()
                }
    }
}
