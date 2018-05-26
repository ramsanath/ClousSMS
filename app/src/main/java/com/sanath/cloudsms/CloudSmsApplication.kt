package com.sanath.cloudsms

import android.app.Application
import com.sanath.cloudsms.di.AppComponent
import com.sanath.cloudsms.di.DaggerAppComponent
import com.sanath.cloudsms.di.modules.ContextModule

/**
 * Created by sanath on 21/05/18.
 */
class CloudSmsApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .contextModule(ContextModule(this))
                .build()
    }

    /*
    Make the AppComponent which provides the dependencies available statically so
    that we can access it directly without methods like "getApplicationContext() as CloudSmsApplication".
     */
    companion object {
        lateinit var component: AppComponent
        private set
    }
}