package com.bbalabsi.whatsaver.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bbalabsi.whatsaver.data.dao.UserDao
import com.bbalabsi.whatsaver.data.model.User


@Database(entities = [(User::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {



    abstract fun userDao(): UserDao
}