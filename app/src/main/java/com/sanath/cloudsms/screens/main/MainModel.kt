package com.sanath.cloudsms.screens.main

import android.util.Log
import com.sanath.cloudsms.auth.UserManager
import com.sanath.cloudsms.di.Injector
import com.sanath.cloudsms.models.User
import com.sanath.smswrapper.SmsConstants
import com.sanath.smswrapper.models.Sms
import com.sanath.smswrapper.sms.SmsFetcher

/**
 * Created by sanath on 22/05/18.
 */
class MainModel : MainContract.Model {
    private val tag = this::class.java.name

    private val userManager = UserManager()

    private var messages: List<Sms>? = null
    private var selectedItems: List<Sms> = ArrayList()

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

    override fun getMessages(): List<Sms> {
        if (messages == null) {
            messages = SmsFetcher
                    .builder(Injector.inject().context())
                    .from(SmsConstants.MsgBox.ALL)
                    .get()
        }
        return messages!!
    }

    override fun getSelectedItems() = selectedItems

    override fun addSelectedItem(item: Sms) {
        selectedItems += item
    }

    override fun removeSelectedItem(item: Sms) {
        selectedItems -= item
    }

    override fun clearSelectedItems() {
        selectedItems = ArrayList()
    }

    override fun isItemSelected(item: Sms) = selectedItems.contains(item)
}