package com.bbalabsi.base

import android.app.Application
import com.bbalabsi.base.di.components.BaseNetworkComponent
import com.bbalabsi.base.di.components.DaggerBaseNetworkComponent
import com.bbalabsi.base.di.modules.BaseAppModule
import timber.log.Timber

open class BaseApplication : Application() {

    protected lateinit var baseDaggerNetworkComponent: BaseNetworkComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        baseDaggerNetworkComponent =
            DaggerBaseNetworkComponent.builder()
                .baseAppModule(BaseAppModule(this))
                .build()
    }
}