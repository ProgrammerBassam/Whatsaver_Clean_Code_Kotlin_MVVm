package com.bbalabsi.whatsaver.activity.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.bbalabsi.base.liveUtils.SingleLiveEvent
import com.bbalabsi.base.repo.Resource
import com.bbalabsi.whatsaver.WhatsaverApp
import com.bbalabsi.whatsaver.data.model.User
import com.bbalabsi.whatsaver.data.repository.UserRepository
import com.bbalabsi.whatsaver.di.components.DaggerActivityComponent
import javax.inject.Inject

class UserViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var userRepository: UserRepository
    val user: LiveData<Resource<User>>? = null
    val userSelectionListener = SingleLiveEvent<User>()


    init {
        DaggerActivityComponent.builder()
            .baseNetworkComponent(WhatsaverApp.baseNetworkComponent)
            .build()
            .inject(this)
    }

    fun loadUser(user: User) {
        userSelectionListener.value = user
    }

    fun getUserById(userId: String) = userRepository.getUserById(userId)

    fun updatePoints(userId: Int, points: Int) = userRepository.updatePoints(userId, points)

}