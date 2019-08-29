package com.bbalabsi.whatsaver.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bbalabsi.base.repo.BaseDao
import com.bbalabsi.whatsaver.data.model.User

@Dao
interface UserDao : BaseDao<User> {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    @Query("DELETE FROM User WHERE user_id=:user_id")
    fun deleteUser(user_id: String)

    @Query("SELECT * FROM User WHERE user_id=:user_id")
    fun getUser(user_id: String): LiveData<User>

    @Query("UPDATE User SET points = (points + :points) WHERE id=:id")
    fun updateUser(id: Int, points: Int)
}