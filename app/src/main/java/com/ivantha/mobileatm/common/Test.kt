package com.ivantha.mobileatm.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ivantha.mobileatm.model.Account
import com.ivantha.mobileatm.model.Deal
import com.ivantha.mobileatm.model.User

object Test {
    fun addTestData() {
        clearData()
        addUser()
        addDeals()
    }

    private fun clearData() {
        FirebaseDatabase.getInstance().getReference("/").setValue(null)
    }

    private fun addUser() {
        val user = User()
        user.firstName = "Oshan"
        user.lastName = "Mudannayake"
        user.email = "oshan.ivantha@gmail.com"
        user.seed = "VKN9VNOZUFMWMMIUVZLVXUTFPWRGQQBNGEYWBHUYQMXNKPDDFAHVQJKCQRHUYHGRBLCIHDWHUGK99FHCI"

        val account = Account()
        account.spendingLimit = 100.0
        user.account = account

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance()!!.currentUser!!.uid).setValue(user)
    }

    private fun addDeals() {
        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("Some Pizza",
                "You will never get a discount like this!",
                "CrazyHut",
                "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"))
        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("Party frocks",
                "Clothes of your dreams",
                "Odel",
                "https://www.dhresource.com/0x0s/f2-albu-g5-M00-C1-99-rBVaJFhozYiATXqgAAH1iciKmqo369.jpg/2017-spring-kids-birthday-baby-party-wear.jpg"))
    }
}