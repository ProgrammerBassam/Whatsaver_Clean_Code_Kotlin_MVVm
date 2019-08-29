package com.bbalabsi.whatsaver.fragment.about

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.bbalabsi.base.toast
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.ReplaceFragmentListener
import com.maciejkozlowski.fragmentutils_kt.getListenerOrThrowException
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutUsFragment @SuppressLint("ValidFragment")
private constructor() : Fragment() {



    companion object {
        const val TAG = "4"
        fun newInstance() = AboutUsFragment()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val adsElement = Element()
        adsElement.title = getString(R.string.ads_with_us_string)
        adsElement.setOnClickListener {

            toast(getString(R.string.adver_string), true)

        }

        val aboutPage = AboutPage(context)
            .isRTL(resources.getBoolean(R.bool.isRtl))
            .setImage(R.drawable.dummy_image)
            .addItem(Element().setTitle(getString(R.string.version_string) + " 2.0"))
            .addItem(adsElement)
            .setDescription(getString(R.string.about_desc_string))
            .addGroup(getString(R.string.connect_with_us_string))
            .addEmail("bassam.badr200@gmail.com")
            .addTwitter("Alabsi_Bassam")
            .addGitHub("ProgrammerBassam")
            .create()


        return aboutPage
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu.forEach {

            it.isVisible = false

        }
    }
}