package com.sanath.cloudsms.auth

import com.google.gson.Gson
import com.sanath.cloudsms.di.Injector
import com.sanath.cloudsms.models.User
import com.sanath.cloudsms.storage.LocalStorage
import com.sanath.cloudsms.storage.StorageKeys

/**
 * Created by sanath on 24/05/18.
 *
 * A class that manager registering, signingIn, signingOut, ect users.
 */
class UserManager {

    private val localStorage: LocalStorage = Injector.inject().localStorage()

    private val gson = Gson()

    fun signInUser(user: User) {
        val userJson = gson.toJson(user)
        localStorage.put(StorageKeys.CURRENT_USER.name, userJson)
    }

    fun signOutUser() {
        localStorage.remove(StorageKeys.CURRENT_USER.name)
    }

    fun getCurrentUser(): User? {
        val userJson = localStorage.get(StorageKeys.CURRENT_USER.name, null) ?: return null
        return Gson().fromJson(userJson, User::class.java)
    }

    fun isUserSignedIn(): Boolean {
        return localStorage.contains(StorageKeys.CURRENT_USER.name)
    }

    fun registerUser(user: User): User? {
        //TODO("not implemented")
        return null
    }
}