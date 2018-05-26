package com.sanath.cloudsms.di

import com.sanath.cloudsms.CloudSmsApplication

/**
 * Created by sanath on 22/05/18.
 *
 * This is an abstraction over the AppComponent instance that is in the CloudSmSApplication.
 *
 */
class Injector private constructor() {

    companion object {
        /**
         * Gets the appComponent.
         */
        fun inject() : AppComponent = CloudSmsApplication.component
    }
}