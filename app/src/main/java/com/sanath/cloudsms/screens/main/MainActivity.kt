package com.sanath.cloudsms.screens.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.sanath.cloudsms.R
import com.sanath.cloudsms.commons.Helper
import com.sanath.cloudsms.di.Injector
import com.sanath.cloudsms.screens.messages.MessageListFragment
import com.sanath.smswrapper.SmsConstants
import com.sanath.smswrapper.sms.SmsFetcher
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.component_toolbar.*


class MainActivity : AppCompatActivity(), MainContract.View {

    private val tag = this::class.java.name
    private val googleAuth = Injector.inject().googleAuth()

    /*
    Keep a reference to the account menu item in the toolbar
    So that we change change the icon of it dynamically based on the
    signedIn status of the user.
     */
    private lateinit var accountMenuItem: MenuItem

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = MainPresenter(this)

        //  For testing purpose only
        val messages = SmsFetcher.builder(this)
                .from(SmsConstants.MsgBox.ALL)
                .limit(2)
                .sortAscBy(SmsConstants.Attributes.DATE)
                .get()

        val fragmentTransaction = fragmentManager.beginTransaction()
        val messageListFragment = MessageListFragment()

        Helper.putMessageArgs(messageListFragment, messages)

        fragmentTransaction.add(fragmentContainer.id, messageListFragment, "Messages")
        fragmentTransaction.commit()
        //  End
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        accountMenuItem = menu.findItem(R.id.action_account)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            //TODO("settings page")
            true
        }
        R.id.action_account -> {
            presenter.handleAccountButtonClicked()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            presenter.handleSignInSuccess(Helper.getUserFromIntent(data!!))
        } catch (e: ApiException) {
            Log.w(tag, "signInResult:failed code = " + e.statusCode)
            presenter.handleSignInFailure(e.message!!)
        } catch (e: Exception) {
            presenter.handleSignInFailure(e.message!!)
        }
    }

    override fun attemptGoogleSignIn() {
        googleAuth.signIn(this)
    }

    override fun toggleAccountIcon(flag: Boolean) {
        if (flag) {
            accountMenuItem.icon = Helper.getDrawable(this, R.drawable.ic_person_white_24dp)
        } else {
            accountMenuItem.icon = Helper.getDrawable(this, R.drawable.ic_person_outline_white_24dp)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}