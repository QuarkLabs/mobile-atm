package com.ivantha.mobileatm.model

import java.io.Serializable
import java.util.*

class Account : Serializable {
    var balance: Double = 0.0

    var spendingLimitEnable = true
    var spendingLimit: Double = 0.0


}