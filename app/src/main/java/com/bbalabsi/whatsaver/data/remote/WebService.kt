package com.bbalabsi.whatsaver.data.remote

import androidx.lifecycle.LiveData
import com.bbalabsi.base.api.ApiResponse
import com.bbalabsi.whatsaver.data.source.UserSource
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("firstLogin.php")
    fun getPoints(@Query("user_id")  user_id: String): LiveData<ApiResponse<UserSource>>

    @GET("updatePoints.php")
    fun updatePoints(@Query("user_id")  user_id: Int, @Query("points")  points: Int):
            LiveData<ApiResponse<UserSource>>
}