package com.sanath.cloudsms.models

import java.util.*

/**
 * Created by sanath on 21/05/18.
 */
data class User
(
        var id: Int?,
        var name: String?,
        var email: String?,
        var photoUrl: String?,
        var numbers: Array<String>?,
        var joinedDate: Date?
) {

    /**
     * Creates user from google account which has limited information
     */
    constructor(googleAccount: GoogleUserAccount) : this(
            null,
            googleAccount.name,
            googleAccount.email,
            googleAccount.photoUrl,
            null,
            null
    )

    constructor() : this(null, null, null, null, null, null)
}