package com.sanath.cloudsms.screens.main

import com.sanath.cloudsms.models.GoogleUserAccount
import com.sanath.cloudsms.models.User

/**
 * Created by sanath on 21/05/18.
 */
interface MainContract {

    interface View {
        fun toggleAccountIcon(flag: Boolean)
        fun attemptGoogleSignIn()
        fun showMessage(message: String)
    }

    interface Presenter {
        fun handleSignInSuccess(account: GoogleUserAccount)
        fun handleSignInFailure(error: String)
        fun handleAccountButtonClicked()
    }

    interface Model {
        fun isUserSignedIn(): Boolean
        fun signInUser(user: User)
        fun signOutUser()
    }
}