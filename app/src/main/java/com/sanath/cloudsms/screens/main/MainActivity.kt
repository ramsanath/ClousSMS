package com.sanath.cloudsms.screens.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.common.api.ApiException
import com.sanath.cloudsms.R
import com.sanath.cloudsms.commons.Helper
import com.sanath.cloudsms.di.Injector
import com.sanath.cloudsms.screens.RecyclerItemClickListener
import com.sanath.cloudsms.screens.messages.MessageAdapter
import com.sanath.smswrapper.models.Sms
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.component_toolbar.*


class MainActivity : AppCompatActivity(), MainContract.View, ActionMode.Callback {

    private val tag = this::class.java.name
    private val googleAuth = Injector.inject().googleAuth()

    private var actionMode: ActionMode? = null
    private var isMultiSelectMode = false
    private lateinit var selectedItems: List<Sms>

    private lateinit var listAdapter: MessageAdapter

    /*
    Keep a reference to the account menu item in the toolbar
    So that we change change the icon of it dynamically based on the
    signedIn status of the user.
     */
    private lateinit var accountMenuItem: MenuItem

    private lateinit var presenter: MainContract.Presenter


    //  Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        presenter = MainPresenter(this)

        //  Initialise with an empty list
        listAdapter = MessageAdapter(this, ArrayList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listAdapter

        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this,
                recyclerView, object : RecyclerItemClickListener.OnItemClickListener {

            override fun onItemClick(view: View, position: Int) {
                presenter.handleListItemClicked(position)
            }

            override fun onItemLongClick(view: View, position: Int) {
                presenter.handleListItemLongPressed(position)
            }
        }))
        presenter.onViewCreated()
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

    //  End


    // ActionMode.Callback

    override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
        when (menu?.itemId) {
            R.id.action_delete -> {
                Toast.makeText(this@MainActivity, selectedItems.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.activity_main_action_mode, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        presenter.handleExitMultiSelectMode()
    }

    //  End


    //  MainContract.View

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

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun enterMultiSelectMode() {
        isMultiSelectMode = true
        selectedItems = ArrayList()

        if (actionMode == null) {
            actionMode = startActionMode(this@MainActivity)
        }
    }

    override fun exitMultiSelectMode() {
        actionMode?.title = ""
        actionMode?.finish()
        actionMode = null
        isMultiSelectMode = false
        selectedItems = ArrayList()
        listAdapter.setSelectedItems(ArrayList())
    }

    override fun showMessagesList(messages: List<Sms>) {
        listAdapter.setData(messages)
        listAdapter.setSelectedItems(ArrayList())
    }

    override fun highlightSelectedItem(item: Sms) {
        selectedItems += item
        listAdapter.setSelectedItems(selectedItems)
        actionMode?.title = selectedItems.size.toString()
    }

    override fun unhighlightSelectedItem(item: Sms) {
        selectedItems -= item
        listAdapter.setSelectedItems(selectedItems)
        actionMode?.title = selectedItems.size.toString()
    }

    override fun isMultiSelectionMode() = isMultiSelectMode

    //  End
}