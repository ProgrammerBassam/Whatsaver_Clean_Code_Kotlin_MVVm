package com.bbalabsi.whatsaver.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bbalabsi.base.BaseActivity
import com.bbalabsi.base.repo.Status
import com.bbalabsi.base.toast
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.MainActivity
import com.bbalabsi.whatsaver.utils.Const
import com.bbalabsi.whatsaver.utils.SharedPreference
import com.google.android.gms.ads.MobileAds
import com.onesignal.OneSignal
import timber.log.Timber

class SplashActivity : BaseActivity() {

    private lateinit var userViewModel: UserViewModel

    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        MobileAds.initialize(
            this
        ) { }


        userViewModel    = ViewModelProvider(this).get(UserViewModel::class.java)
        sharedPreference = SharedPreference(this)

        if (sharedPreference.getValueBoolien(Const.firstLogin, false))
        {
            openMain()
        }
        else
        {
            OneSignal.idsAvailable { userId, registrationId ->

                userViewModel.getUserById(userId)
                    .observe(this@SplashActivity, Observer {
                        when (it?.status) {
                            Status.SUCCESS -> {
                                it.data?.let {



                                    sharedPreference.save(Const.id, it.id)
                                    sharedPreference.save(Const.points, it.points)
                                    sharedPreference.save(Const.userId, it.user_id)

                                    sharedPreference.save(Const.firstLogin, true)

                                    openMain()
                                }
                                //             countingIdleResources.decrement()
                            }
                            Status.ERROR -> {

                                println("steeeeep 2")

                                if (sharedPreference.getValueBoolien(Const.firstLogin, false))
                                {
                                    openMain()
                                }
                                else
                                {
                                    toast(getString(R.string.internet_first_time_string),
                                        true)
                                }
                            }
                            Status.LOADING -> {

                                println("steeeeep 3")

                                Timber.e("Loading")
                                it.data?.let {

                                    sharedPreference.save(Const.id, it.id)
                                    sharedPreference.save(Const.points, it.points)
                                    sharedPreference.save(Const.userId, it.user_id)

                                    sharedPreference.save(Const.firstLogin, true)

                                    openMain()

                                }
                            }
                        }
                    })

            }
        }



    }

    fun openMain()
    {
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, 2000)
    }
}
