package com.sanath.cloudsms.di

import android.content.Context
import com.sanath.cloudsms.auth.GoogleAuth
import com.sanath.cloudsms.di.modules.AuthModule
import com.sanath.cloudsms.di.modules.ContextModule
import com.sanath.cloudsms.di.modules.StorageModule
import com.sanath.cloudsms.storage.LocalStorage
import dagger.Component
import javax.inject.Singleton

/**
 * Created by sanath on 22/05/18.
 */
@Singleton
@Component(modules = arrayOf(
        ContextModule::class,
        AuthModule::class,
        StorageModule::class))
interface AppComponent {
    fun context(): Context
    fun googleAuth(): GoogleAuth
    fun localStorage(): LocalStorage
}