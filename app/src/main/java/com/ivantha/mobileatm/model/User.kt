package com.ivantha.mobileatm.model

import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

class User : Serializable {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null

    var seed: String? = null

    var account: Account? = null
    //var photoURL: String? = null
    var log: HashMap<String, Transaction> = HashMap()
}
