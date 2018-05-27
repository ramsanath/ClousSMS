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
        view.showToast("Successfully signed in")
        model.signInUser(User(account))
    }

    override fun handleListItemLongPressed(position: Int) {
        if (!view.isMultiSelectionMode()) {
            view.enterMultiSelectMode()
        }
        multiSelect(position)
    }

    override fun handleListItemClicked(position: Int) {
        if (view.isMultiSelectionMode()) {
            multiSelect(position)
        } else {
            //  TODO("Open message screen")
        }
    }

    override fun onViewCreated() {
        view.showMessagesList(model.getMessages())
    }

    override fun handleExitMultiSelectMode() {
        view.exitMultiSelectMode()
    }

    private fun multiSelect(position: Int) {
        val message = model.getMessages()[position]

        if (!model.isItemSelected(message)) {
            model.addSelectedItem(message)
            view.highlightSelectedItem(message)
        } else {
            model.removeSelectedItem(message)
            view.unhighlightSelectedItem(message)
            if (model.getSelectedItems().isEmpty()) view.exitMultiSelectMode()
        }
    }
}