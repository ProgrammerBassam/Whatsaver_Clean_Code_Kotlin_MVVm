package com.bbalabsi.whatsaver.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.bbalabsi.base.BaseFragment
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.MainActivity
import com.bbalabsi.whatsaver.fragment.home.recent_fragment.RecentFragment
import com.bbalabsi.whatsaver.fragment.home.save_fragment.SavedFragment
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment @SuppressLint("ValidFragment")
private constructor() : BaseFragment() {

    var mTabAdapter: TabAdapter? = null

    override fun getLayoutId() = R.layout.fragment_home

    companion object {
        const val TAG = "0"
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initTabsAdapter()
        initViewPager()
        initTab()
        highLightCurrentTab(0)
    }

    fun initTabsAdapter()
    {
        mTabAdapter = TabAdapter(fragmentManager!!)

        mTabAdapter!!.addFragment(RecentFragment.newInstance(), getString(R.string.recent_string))
        mTabAdapter!!.addFragment(SavedFragment.newInstance(), getString(R.string.saved_string))
    }

    fun initViewPager()
    {
        mViewPager.adapter = mTabAdapter

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                highLightCurrentTab(position)
                MainActivity.pos = position

                if (MainActivity.pos === 0) {
                    RecentFragment.type = MainActivity.Tpos
                  //  RecentFragment.loadMedia()
                }
                else
                {
                    SavedFragment.type = MainActivity.Tpos
                 //   SavedFragment.loadMedia()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }

    fun initTab()
    {
        mTabLayout.setupWithViewPager(mViewPager)
    }

    private fun highLightCurrentTab(position: Int) {

        for (i in 0 until mTabLayout.tabCount)
        {
            val tab = mTabLayout.getTabAt(i)!!
            tab.customView = null
            tab.customView = mTabAdapter!!.getTabView(i, context!!)
        }

        val tab = mTabLayout.getTabAt(position)!!
        tab.customView = null
        tab.customView = mTabAdapter!!.getSelectedTabView(position, context!!)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_refresh)
        {
            if (MainActivity.pos === 0) {
                RecentFragment.type = MainActivity.Tpos
                RecentFragment.loadMedia()
            }
            else
            {
                SavedFragment.type = MainActivity.Tpos
                SavedFragment.loadMedia()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}