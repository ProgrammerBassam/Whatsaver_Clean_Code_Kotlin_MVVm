package com.bbalabsi.whatsaver.di.components

import com.bbalabsi.base.di.components.BaseNetworkComponent
import com.bbalabsi.whatsaver.activity.splash.UserViewModel
import com.bbalabsi.whatsaver.di.modules.AppComponent
import com.bbalabsi.whatsaver.di.scopes.AppScope
import dagger.Component

@AppScope
@Component(dependencies = [BaseNetworkComponent::class], modules = [AppComponent::class])
interface ActivityComponent {



    fun inject(userViewModel: UserViewModel)
}
