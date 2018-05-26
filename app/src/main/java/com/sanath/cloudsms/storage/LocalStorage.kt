package com.sanath.cloudsms.storage

/**
 * Created by sanath on 24/05/18.
 *
 */
interface LocalStorage {
    fun put(key: String, value: String)
    fun get(key: String, default: String?): String?
    fun contains(key: String): Boolean
    fun remove(key: String)
    fun flush()
}