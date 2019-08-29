package com.bbalabsi.whatsaver.fragment.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.bbalabsi.whatsaver.R
import java.util.*

class TabAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragmentTitleList = ArrayList<String>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }


    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun getTabView(position: Int, mContext: Context): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null)
        val tabTextView = view.findViewById<TextView>(R.id.tabTextView)
        tabTextView.text = mFragmentTitleList[position]
        return view
    }

    fun getSelectedTabView(position: Int, mContext: Context): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.custom_tab, null)
        val tabTextView = view.findViewById<TextView>(R.id.tabTextView)
        tabTextView.text = mFragmentTitleList[position]
        tabTextView.setTextColor(ContextCompat.getColor(mContext, android.R.color.white))

        return view
    }
}