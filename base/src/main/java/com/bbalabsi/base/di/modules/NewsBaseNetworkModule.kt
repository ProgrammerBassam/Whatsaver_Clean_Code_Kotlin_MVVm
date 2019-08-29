package com.bbalabsi.base.di.modules

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.bbalabsi.base.BuildConfig
import com.bbalabsi.base.api.ApiConstants
import com.bbalabsi.base.api.ApiServices
import com.bbalabsi.base.di.scopes.BaseScope
import com.bbalabsi.base.liveUtils.LiveDataCallAdapterFactory
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
open class NewsBaseNetworkModule {

    private val mBaseUrl = "https://whatsaver.whatstickersapi.website/AppAPI/"

    @Provides
    @BaseScope
    fun providesSharedPreferences(application: Application): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @BaseScope
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @BaseScope
    fun provideGson(): Gson = with(GsonBuilder()) {
        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        create()
    }

    @Provides
    @BaseScope
    fun provideBeautyLoggingInterceptor(): LoggingInterceptor = with(LoggingInterceptor.Builder()) {
        loggable(BuildConfig.DEBUG)
        setLevel(Level.BASIC)
        log(Platform.INFO)
        request("Request")
        response("Response")
        addHeader("Version", BuildConfig.VERSION_NAME)
        build()
    }

    @Provides
    @BaseScope
    fun provideOkhttpClient(
        cache: Cache,
        interceptor: HttpLoggingInterceptor,
        beautyloggingInterceptor: LoggingInterceptor): OkHttpClient = with(OkHttpClient.Builder()) {

        connectTimeout(ApiConstants.HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
        readTimeout(ApiConstants.HTTP_READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
        addInterceptor(interceptor)
        build()
    }

    @Provides
    @BaseScope
    open fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(mBaseUrl)
            .client(okHttpClient)
            .build()
    }


    @Provides
    @BaseScope
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @BaseScope
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiServices::class.java)
}
