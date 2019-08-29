package com.bbalabsi.whatsaver.di.modules

import android.app.Application
import androidx.room.Room
import com.bbalabsi.whatsaver.data.AppDatabase
import com.bbalabsi.whatsaver.data.remote.WebService
import com.bbalabsi.whatsaver.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class AppComponent {

    @Provides
    @AppScope
    fun provideRetrofitClient(retrofit: Retrofit) = retrofit.create(WebService::class.java)

    @Provides
    @AppScope
    fun provideRoomDB(application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "whatsaver-db")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @AppScope
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()

}