package com.bbalabsi.whatsaver.fragment.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.bbalabsi.base.toast
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.ReplaceFragmentListener
import com.bbalabsi.whatsaver.utils.Const
import com.bbalabsi.whatsaver.utils.SharedPreference
import com.maciejkozlowski.fragmentutils_kt.getListenerOrThrowException
import com.onesignal.OneSignal

class SettingsFragment @SuppressLint("ValidFragment")
private constructor() : PreferenceFragmentCompat() {

    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    companion object {
        const val TAG = "2"
        fun newInstance() = SettingsFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = getListenerOrThrowException(ReplaceFragmentListener::class.java)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_pref, rootKey)

        val sharedPreference = SharedPreference(context!!)

        if (sharedPreference.getValueBoolien(Const.showNotification, true))
        {
            findPreference<SwitchPreferenceCompat>("notifications")!!.setDefaultValue(true)
        }
        else
        {
            findPreference<SwitchPreferenceCompat>("notifications")!!.setDefaultValue(false)
        }


        findPreference<Preference>("feedback")!!.setOnPreferenceClickListener {

            whatsappMe(getString(R.string.whatsapp_number_string))
            replaceFragmentListener.addPoints(10)

            true
        }

        findPreference<SwitchPreferenceCompat>("notifications")!!.setOnPreferenceChangeListener {
                preference, newValue ->


                    if (newValue as Boolean)
                    {
                        OneSignal.setSubscription(true)
                    }
                    else
                    {
                        OneSignal.setSubscription(false)
                    }

                    sharedPreference.save(Const.showNotification, newValue)

                true
        }
    }

    fun whatsappMe(numberWithCountryCode: String)
    {
        try
        {
            val uri = Uri.parse("https://api.whatsapp.com/send?phone=" + numberWithCountryCode)

            val sendIntent =  Intent(Intent.ACTION_VIEW, uri)

            startActivity(sendIntent)
        }
        catch(e: Exception)
        {
            toast(getString(R.string.whatsapp_open_error_string), true)
        }
    }
}