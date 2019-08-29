package com.bbalabsi.whatsaver.fragment.privacy_policy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import androidx.core.view.forEach
import com.bbalabsi.base.BaseFragment
import com.bbalabsi.whatsaver.R
import kotlinx.android.synthetic.main.fragment_privacy_policy.*

class PolicyPrivacyFragment @SuppressLint("ValidFragment")
private constructor() : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_privacy_policy

    companion object {
        const val TAG = "3"
        fun newInstance() = PolicyPrivacyFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mWebView.settings.apply {
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }

        mWebView.loadUrl("file:///android_asset/privacy_policy.html")
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        menu.forEach {

            it.isVisible = false

        }
    }
}