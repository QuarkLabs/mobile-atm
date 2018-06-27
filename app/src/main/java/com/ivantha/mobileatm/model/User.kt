package com.ivantha.mobileatm.model

import java.io.Serializable

class User : Serializable {
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null

    var seed: String? = null

    var account: Account? = null
    var photoURL: String? = null
}
