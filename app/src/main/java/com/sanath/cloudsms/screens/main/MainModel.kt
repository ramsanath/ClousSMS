package com.sanath.cloudsms.screens.main

import android.util.Log
import com.sanath.cloudsms.auth.UserManager
import com.sanath.cloudsms.models.User

/**
 * Created by sanath on 22/05/18.
 */
class MainModel : MainContract.Model {
    private val tag = this::class.java.name

    private val userManager = UserManager()

    override fun isUserSignedIn(): Boolean {
        Log.i(tag, userManager.isUserSignedIn().toString())
        return userManager.isUserSignedIn()
    }

    override fun signInUser(user: User) {
        userManager.signInUser(user)
    }

    override fun signOutUser() {
        userManager.signOutUser()
    }

}