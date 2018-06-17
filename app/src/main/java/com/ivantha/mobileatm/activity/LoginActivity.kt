package com.ivantha.mobileatm.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ivantha.mobileatm.R
import com.ivantha.mobileatm.common.Session
import com.ivantha.mobileatm.common.Test
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // Navigate to MainActivity if the user is already logged in
        if (firebaseAuth!!.currentUser != null) {
            Toast.makeText(applicationContext, "Already logged in", Toast.LENGTH_SHORT).show()

            Test.addTestData()

            // A value access is essential to start init() method in Session
            println(Session.currentUser)

            showMainActivity()
        } else {
            setContentView(R.layout.activity_login)
        }
    }

    fun onClickLoginActivitySignInButton(view: View) {
        val email = loginActivityEmailEditText.text.toString()
        val password = loginActivityPasswordEditText.text.toString()

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
                val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
                this@LoginActivity.startActivity(myIntent)
                Toast.makeText(applicationContext, "Login success", Toast.LENGTH_SHORT).show()
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
        val myIntent = Intent(this@LoginActivity, MainActivity::class.java)
        this@LoginActivity.startActivity(myIntent)
    }

}
