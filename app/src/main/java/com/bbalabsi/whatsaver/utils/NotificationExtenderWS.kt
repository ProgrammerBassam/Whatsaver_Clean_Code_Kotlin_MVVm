package com.bbalabsi.whatsaver.utils


import com.onesignal.OSNotificationReceivedResult
import com.onesignal.NotificationExtenderService


class NotificationExtenderWS : NotificationExtenderService() {
    override fun onNotificationProcessing(receivedResult: OSNotificationReceivedResult): Boolean {

        val sharedPreference = SharedPreference(applicationContext)

        sharedPreference.save(Const.points_not, sharedPreference.getValueInt(Const.points_not)
                + 50)


        return true
    }
}