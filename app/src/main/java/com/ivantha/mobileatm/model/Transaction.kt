package com.ivantha.mobileatm.model

import com.ivantha.mobileatm.common.Session
import org.joda.time.DateTime
import java.io.Serializable

class Transaction(var intention: Intention, var amount: Double) : Serializable {
    var timestamp: DateTime = DateTime()
    var confirmed: Boolean = false
    var title: String? = null
    var description: String? = null
    var initiatorId: String? = null
    var initiatorName: String? = null
    var receiverId: String? = null

    /**
     * Intention of starting the transaction
     */
    enum class Intention {
        SEND,
        REQUEST
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
