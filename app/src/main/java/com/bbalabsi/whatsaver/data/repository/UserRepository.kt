package com.bbalabsi.whatsaver.data.repository

import androidx.lifecycle.LiveData
import com.bbalabsi.base.repo.AppExecutors
import com.bbalabsi.base.repo.NetworkBoundResource
import com.bbalabsi.base.repo.Resource
import com.bbalabsi.whatsaver.data.dao.UserDao
import com.bbalabsi.whatsaver.data.model.User
import com.bbalabsi.whatsaver.data.remote.WebService
import com.bbalabsi.whatsaver.data.source.UserSource
import javax.inject.Inject

class UserRepository @Inject constructor(val userDao: UserDao,
                                         val webService: WebService
) {

    val appExecutors: AppExecutors = AppExecutors()



    fun getUserById(userId: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, UserSource>(appExecutors) {
            override fun saveCallResult(item: UserSource) {
                userDao.deleteUser(userId)
                userDao.insertUser(item.user!!)
            }

            override fun shouldFetch(data: User?) = true

            override fun loadFromDb() = userDao.getUser(userId)

            override fun createCall() = webService.getPoints(userId)
        }.asLiveData()
    }

    fun updatePoints(userId: Int, points: Int): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, UserSource>(appExecutors) {
            override fun saveCallResult(item: UserSource) {

                userDao.updateUser(userId, points)
            }

            override fun shouldFetch(data: User?) = true

            override fun loadFromDb() = userDao.getUser("" + userId)

            override fun createCall() = webService.updatePoints(userId, points)
        }.asLiveData()
    }


}