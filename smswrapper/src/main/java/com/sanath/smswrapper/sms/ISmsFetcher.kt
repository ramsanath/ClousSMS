package com.sanath.smswrapper.sms

import com.sanath.smswrapper.models.Sms

/**
 * Created by sanath on 17/05/18.
 */
interface ISmsFetcher {
    fun from(from: String): ISmsFetcher
    fun limit(limit: Int): ISmsFetcher
    fun sortAscBy(value: String): ISmsFetcher
    fun sortDescBy(value: String): ISmsFetcher
    fun get(): List<Sms>
}