package com.ivantha.mobileatm.model

import java.math.BigDecimal
import java.util.*

class Account {
    var balance = BigDecimal(0.0)
    var transactions: List<Transaction> = LinkedList()

    var spendingLimitEnable = true
    var spendingLimit = BigDecimal(100.0)

    fun setSpendingLimit(spendingLimit: Double) {
        this.spendingLimit = BigDecimal.valueOf(spendingLimit)
    }
}