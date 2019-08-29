package com.bbalabsi.base.di.components

import android.app.Application
import com.bbalabsi.base.api.ApiServices
import com.bbalabsi.base.di.modules.BaseAppModule
import com.bbalabsi.base.di.modules.NewsBaseNetworkModule
import com.bbalabsi.base.di.scopes.BaseScope
import dagger.Component
import retrofit2.Retrofit

@BaseScope
@Component(modules = [BaseAppModule::class, NewsBaseNetworkModule::class])
interface BaseNetworkComponent {
    // downstream components need these exposed with the return type
    // method name does not really matter
    val apiService: ApiServices
    val application: Application
    val retrofit: Retrofit
}