package com.bbalabsi.whatsaver

import android.content.Context
import androidx.multidex.MultiDex
import com.bbalabsi.base.BaseApplication
import com.bbalabsi.base.di.components.BaseNetworkComponent
import com.onesignal.OneSignal


open class WhatsaverApp : BaseApplication() {

    companion object {
        lateinit var baseNetworkComponent: BaseNetworkComponent
    }

    override fun onCreate() {
        super.onCreate()
        baseNetworkComponent = baseDaggerNetworkComponent

        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}