package com.ivantha.mobileatm.model

import java.io.Serializable

class Deal : Serializable {
    var title: String? = null
    var description: String? = null
    var place: String? = null
    var imageUrl: String? = null

    constructor()

    constructor(title: String?, description: String?, place: String?, imageUrl: String?) {
        this.title = title
        this.description = description
        this.place = place
        this.imageUrl = imageUrl
    }

}
