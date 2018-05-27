package com.sanath.cloudsms.screens.main

import com.sanath.cloudsms.models.GoogleUserAccount
import com.sanath.cloudsms.models.User
import com.sanath.smswrapper.models.Sms

/**
 * Created by sanath on 21/05/18.
 */
interface MainContract {

    interface View {
        fun toggleAccountIcon(flag: Boolean)
        fun attemptGoogleSignIn()
        fun showToast(message: String)
        fun enterMultiSelectMode()
        fun exitMultiSelectMode()
        fun showMessagesList(messages: List<Sms>)
        fun highlightSelectedItem(item: Sms)
        fun unhighlightSelectedItem(item: Sms)
        fun isMultiSelectionMode(): Boolean
    }

    interface Presenter {
        fun onViewCreated()
        fun handleSignInSuccess(account: GoogleUserAccount)
        fun handleSignInFailure(error: String)
        fun handleAccountButtonClicked()
        fun handleListItemLongPressed(position: Int)
        fun handleListItemClicked(position: Int)
        fun handleExitMultiSelectMode()
    }

    interface Model {
        fun isUserSignedIn(): Boolean
        fun signInUser(user: User)
        fun signOutUser()
        fun getMessages(): List<Sms>
        fun getSelectedItems(): List<Sms>
        fun addSelectedItem(item: Sms)
        fun removeSelectedItem(item: Sms)
        fun clearSelectedItems()
        fun isItemSelected(item: Sms): Boolean
    }
}