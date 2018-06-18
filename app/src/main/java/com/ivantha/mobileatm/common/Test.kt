package com.ivantha.mobileatm.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.ivantha.mobileatm.model.Account
import com.ivantha.mobileatm.model.Deal
import com.ivantha.mobileatm.model.Transaction
import com.ivantha.mobileatm.model.User

object Test {
    @JvmStatic
    fun addTestData() {
        clearData()
        addUsers()
        addDeals()
        addLogs()
    }

    @JvmStatic
    private fun clearData() {
        FirebaseDatabase.getInstance().getReference("/").setValue(null)
    }

    @JvmStatic
    private fun addUsers() {
        val user1 = User()
        user1.firstName = "Oshan"
        user1.lastName = "Mudannayake"
        user1.email = "oshan.ivantha@gmail.com"
        user1.seed = "VKN9VNOZUFMWMMIUVZLVXUTFPWRGQQBNGEYWBHUYQMXNKPDDFAHVQJKCQRHUYHGRBLCIHDWHUGK99FHCI"

        val account1 = Account()
        account1.balance = 200.0
        account1.spendingLimit = 100.0
        user1.account = account1

        val user2 = User()
        user2.firstName = "Upeksha"
        user2.lastName = "Liyanage"
        user2.email = "upeksha.4nnet@gmail.com"
        user2.seed = "HSYYCLWNXHKTILDNRIBYJZVE9JGMSADOTXEEGSCLNEQLIHQHVNQWOOWKKERQKQT9MO9QRFAIWLSKHZKQT"

        val account2 = Account()
        account1.balance = 250.0
        account2.spendingLimit = 50.0
        user2.account = account2

        FirebaseDatabase.getInstance().getReference("users").child("qsp8g1byz5hs84xzDywfCtmc2P72").setValue(user1)
        FirebaseDatabase.getInstance().getReference("users").child("MThDUb24tsgDIiOqy3rPvYITj9l2").setValue(user2)
    }

    @JvmStatic
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

    @JvmStatic
    private fun addLogs() {
        val transaction1 = Transaction(Transaction.Intention.SEND, 17.0)
        transaction1.title = "Test 1"
        transaction1.description = "A random transaction"
        transaction1.initiatorId = "MThDUb24tsgDIiOqy3rPvYITj9l2"
        transaction1.initiatorName = "Upeksha Liyanage"

        val transaction2 = Transaction(Transaction.Intention.SEND, 4.50)
        transaction2.title = "2nd"
        transaction2.description = "The second transaction"
        transaction2.initiatorId = "MThDUb24tsgDIiOqy3rPvYITj9l2"
        transaction2.initiatorName = "Upeksha Liyanage"

        val transaction3 = Transaction(Transaction.Intention.SEND, 123.0)
        transaction3.title = "Monthly rent"
        transaction3.description = "Automatic payment"
        transaction3.initiatorId = "MThDUb24tsgDIiOqy3rPvYITj9l2"
        transaction3.initiatorName = "Upeksha Liyanage"

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("log").push().setValue(transaction1)
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("log").push().setValue(transaction2)
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("log").push().setValue(transaction3)
    }
}