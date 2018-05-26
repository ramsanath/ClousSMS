package com.sanath.cloudsms.di.modules

import android.content.Context
import android.support.annotation.NonNull
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sanath on 21/05/18.
 */
@Module
class ContextModule(private val context: Context) {

    @Singleton
    @Provides
    @NonNull
    fun context() = context

}