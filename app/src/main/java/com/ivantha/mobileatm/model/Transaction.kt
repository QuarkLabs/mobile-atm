package com.ivantha.mobileatm.model

import com.ivantha.mobileatm.common.Session
import org.joda.time.DateTime
import java.io.Serializable

class Transaction : Serializable {
    var intention: Intention? = null
    var amount: Double? = null
    var timestamp: Long = DateTime().toDateTime().getMillis();
    var confirmed: Boolean = false
    var title: String? = null
    var description: String? = null
    var initiatorId: String? = null
    var initiatorName: String? = null
    var receiverId: String? = null

    // Should be deleted later
    var profileImageUrl: String = "https://image.ibb.co/kJ5bsy/user.png"

    /**
     * Intention of starting the transaction
     */
    enum class Intention {
        SEND,
        REQUEST
    }

    constructor()

    constructor(intention: Intention, amount: Double) {
        this.intention = intention
        this.amount = amount
    }

    fun getStatement(): String {
        return title +
                "\n" + description +
                "\n" + "Amount : " + amount +
                "\n" + "Requesting party : " + initiatorId +
                "\n" + "Request : " + intention.toString() +
                "\n" + "Time : " + timestamp
    }

    override fun toString(): String {
        return Session.gson!!.toJson(this)
    }

}
