package com.ivantha.mobileatm.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivantha.mobileatm.model.User


object Session {
    var firebaseUser: FirebaseUser? = null
    var currentUser: User? = null

    private var db = FirebaseDatabase.getInstance().reference

    init {
        // Initialize firebaseUser
        firebaseUser = FirebaseAuth.getInstance().currentUser

        // Initialize currentUser
        db.child("users").child(firebaseUser!!.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentUser = dataSnapshot.getValue(User::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
            }
        })
    }
}
