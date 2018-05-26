package com.sanath.cloudsms.di.modules

import android.content.Context
import com.sanath.cloudsms.storage.LocalStorage
import com.sanath.cloudsms.storage.SharedPrefStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by sanath on 24/05/18.
 */
@Module
class StorageModule {

    @Provides
    @Singleton
    fun localStorage(context: Context): LocalStorage {
        return SharedPrefStorage(context)
    }
}