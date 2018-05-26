package com.sanath.cloudsms.di.modules

import android.content.Context
import com.sanath.cloudsms.auth.GoogleAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sanath on 22/05/18.
 */
@Module
class AuthModule {

    @Provides
    @Singleton
    fun googleAuth(context: Context): GoogleAuth = GoogleAuth(context)

}