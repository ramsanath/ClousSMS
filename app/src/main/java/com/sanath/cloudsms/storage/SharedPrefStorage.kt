package com.sanath.cloudsms.storage

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log

/**
 * Created by sanath on 24/05/18.
 *
 * Implementation of the LocalStorage Interface.
 * Provides basic and simple methods to persist data locally using android's SharedPreferences.
 *
 * Note: Although SharedPreferences allows us to store various primitive
 *       types this is limited only to Strings since anyways objects can be serialised to strings.
 */
class SharedPrefStorage(context: Context) : LocalStorage {
    private val tag = this::class.java.name

    private val sharedPrefFile: String = context.packageName
    private val preferences: SharedPreferences? = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = preferences?.edit()!!

    override fun put(key: String, value: String) {
        editor.putString(key, value).apply()
        Log.i(tag, preferences?.getString(key, "Not present"))
    }

    override fun get(key: String, default: String?): String? {
        return preferences?.getString(key, default)
    }

    override fun contains(key: String): Boolean {
        return this.get(key, null) != null
    }

    override fun remove(key: String) {
        editor.remove(key).apply()
    }

    override fun flush() {
        editor.clear().apply()
    }
}