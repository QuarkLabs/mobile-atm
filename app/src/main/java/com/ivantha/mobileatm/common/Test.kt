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

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("Some Pizza",
                "You will never get a discount like this!",
                "CrazyHut",
                "https://livekindlyproduction-8u6efaq1lwo6x9a.stackpathdns.com/wp-content/uploads/2017/08/pizza-vegan-1280x640.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("Maxi Skirt and Deep Cut Top",
                "Black and Navy Blue Chiffon Printed Maxi Skirt and Deep Cut Top",
                "Zigzag.lk",
                "https://promolk.blob.core.windows.net/deals/promo.lk-deals-6bac3202c8384801bd198246ea12284e.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("Daily Essentials & Household Items",
                "25% Off on Selected Daily Essentials & Household Items",
                "Arpico Supercentre",
                "http://discount365.lk/wp-content/uploads/2018/06/arpico-discount-and-offers-1024x1024.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("50% off",
                "50% off – Cotton Collection Plus",
                "Cotton Collection ",
                "http://discount365.lk/wp-content/uploads/2018/06/cotton-collection-plus-50-discount-1024x1024.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("SHORES OF EUROPE",
                "50% off – SHORES OF EUROPE",
                "Jetwing Travels",
                "http://discount365.lk/wp-content/uploads/2018/06/travel-offer-discount365.lk_-796x1024.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("25% off – Fly to Hambantota",
                "Fly swiftly and comfortably between Colombo and Hambantota on our scheduled flights and enjoy a 25% discount valid upto 30th June 2018.",
                "Cinnamon Air",
                "http://discount365.lk/wp-content/uploads/2018/05/fly-hambantota-cinnaman-air-discount365-1024x532.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("30% Savings on food",
                "NDB Credit Cards 30% Savings on food on Cafe Bagatalle Dine Now",
                "Cafe Bagatalle",
                "http://discount365.lk/wp-content/uploads/2018/05/Cafe-Bagatalle_Discount365_Offers_Discounts_Promotions_Deals_Colombo_Sri-Lanka-1024x863.jpg"))

        FirebaseDatabase.getInstance().getReference("deals").push().setValue(Deal("40% Discount For Vehicle Tyres",
                "Commercial Bank of Ceylon PLC Up to 40% Discount For Credit & Debit cards Vehicle Tyres",
                "Commercial Bank Credit & Debit",
                "http://discount365.lk/wp-content/uploads/2018/02/Commercial-Bank-of-Ceylon-PLC_Discount365_Offers_Discounts_Promotions_Deals_Colombo_Sri-Lanka-1024x535.jpg"))
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


        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("log").push().setValue(transaction1)
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().currentUser!!.uid).child("log").push().setValue(transaction2)
    }
}