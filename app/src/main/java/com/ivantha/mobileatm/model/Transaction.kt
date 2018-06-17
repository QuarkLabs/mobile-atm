package com.ivantha.mobileatm.model

import com.ivantha.mobileatm.common.Session
import org.joda.time.DateTime
import java.io.Serializable

class Transaction(var intention: Intention, var amount: Double) : Serializable {
    var timestamp: DateTime = DateTime()
    var title: String? = null
    var description: String? = null

    enum class Intention {
        SEND,
        REQUEST
    }

    override fun toString(): String {
        return Session.gson.toJson(this)
    }

}
