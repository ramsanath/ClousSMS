package com.sanath.cloudsms.screens.main

import com.sanath.cloudsms.models.GoogleUserAccount
import com.sanath.cloudsms.models.User

/**
 * Created by sanath on 21/05/18.
 */
class MainPresenter : MainContract.Presenter {

    private val view: MainContract.View
    private val model: MainContract.Model

    constructor(view: MainContract.View) {
        this.view = view
        this.model = MainModel()

        //  Set user logged in status on startup
//        if (model.isUserSignedIn()) {
//            this.view.toggleAccountIcon(true)
//        } else {
//            this.view.toggleAccountIcon(false)
//        }
    }

    override fun handleAccountButtonClicked() {
        if (model.isUserSignedIn()) {
            model.signOutUser()
            view.toggleAccountIcon(false)
        } else {
            view.attemptGoogleSignIn()
        }
    }

    override fun handleSignInFailure(error: String) {
        view.toggleAccountIcon(false)
    }

    override fun handleSignInSuccess(account: GoogleUserAccount) {
        view.toggleAccountIcon(true)
        view.showMessage("Successfully signed in")
        model.signInUser(User(account))
    }
}