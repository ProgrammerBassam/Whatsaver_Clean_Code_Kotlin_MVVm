package com.bbalabsi.base.di.modules

import android.app.Application
import com.bbalabsi.base.di.scopes.BaseScope
import dagger.Module
import dagger.Provides

@Module
open class BaseAppModule(private var mApplication: Application) {

    @Provides
    @BaseScope
    open fun provideApplication() = mApplication
}