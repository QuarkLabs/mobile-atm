package com.ivantha.mobileatm.service

import com.ivantha.mobileatm.common.Session
import com.ivantha.mobileatm.model.Transaction

object TransactionServices {
    @JvmStatic
    fun transactionToJson(transaction: Transaction): String{
        return Session.gson.toJson(transaction)
    }

    @JvmStatic
    fun transactionFromJson(json: String): Transaction{
        return Session.gson.fromJson(json, Transaction::class.java)
    }
}