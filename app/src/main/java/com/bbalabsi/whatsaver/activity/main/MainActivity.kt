package com.bbalabsi.whatsaver.activity.main

import android.Manifest
import android.content.Intent
import android.icu.util.LocaleData
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bbalabsi.base.findFragmentByTag
import com.bbalabsi.base.loadFragment
import com.bbalabsi.base.removeFragmentByTag
import com.bbalabsi.base.repo.Status
import com.bbalabsi.base.toast
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.splash.UserViewModel
import com.bbalabsi.whatsaver.fragment.about.AboutUsFragment
import com.bbalabsi.whatsaver.fragment.home.HomeFragment
import com.bbalabsi.whatsaver.fragment.home.recent_fragment.RecentFragment
import com.bbalabsi.whatsaver.fragment.home.save_fragment.SavedFragment
import com.bbalabsi.whatsaver.fragment.points.PointsFragment
import com.bbalabsi.whatsaver.fragment.privacy_policy.PolicyPrivacyFragment
import com.bbalabsi.whatsaver.fragment.settings.SettingsFragment
import com.bbalabsi.whatsaver.utils.Const
import com.bbalabsi.whatsaver.utils.GDPRChecker
import com.bbalabsi.whatsaver.utils.SharedPreference
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.startapp.android.publish.adsCommon.StartAppAd
import com.startapp.android.publish.adsCommon.StartAppSDK
import hotchemi.android.rate.AppRate
import io.ghyeok.stickyswitch.widget.StickySwitch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_ads.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.milliseconds

class MainActivity : AppCompatActivity(), ReplaceFragmentListener {

    private lateinit var sharedPreference: SharedPreference
    private lateinit var mInterstitialAd:  InterstitialAd
    private lateinit var mRewardedAd:      RewardedAd
    private lateinit var userViewModel:    UserViewModel

    private var mAdAdapter: RecyclerView.Adapter<*>?       = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var mAds: ArrayList<AdModel>?                  = null
    private var mHandler: Handler?                         = null
    private var mRunnable: Runnable?                       = null
    private var mCurrentPosition                           = 1


    companion object {
        val TAG  = MainActivity::class.java.simpleName
        var Tpos = 0
        var pos = 0
    }

    val list               = arrayListOf<String>()
    val adslist            = arrayListOf<String>()
    private var fragCounts = 0
    private var en_back    = false

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {

                var fragment = HomeFragment.newInstance()
                var tag      = HomeFragment.TAG




                loadFrag(true,true, tag, fragment,
                    getString(R.string.home_string), R.drawable.ic_home)

                return@OnNavigationItemSelectedListener true
            }


            R.id.navigation_points -> {

                var fragment3 = findFragmentByTag(PointsFragment.TAG)

                if (fragment3 == null)
                {
                    fragment3 = PointsFragment.newInstance()

                }

                loadFrag(false,true, PointsFragment.TAG, fragment3,
                    getString(R.string.points_string), R.drawable.ic_points)

                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_settings -> {

                var fragment4 = findFragmentByTag(SettingsFragment.TAG)

                if (fragment4 == null)
                {
                    fragment4 = SettingsFragment.newInstance()

                }

                loadFrag(false,true, SettingsFragment.TAG, fragment4,
                    getString(R.string.settings_string), R.drawable.ic_settings)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = SharedPreference(this)
        userViewModel    = ViewModelProvider(this).get(UserViewModel::class.java)

        initAds()
        initToolbar()
        initStickSwitch()
        initRecycleViewAd()
        initBottomNavigation()
        initRateApp()
        checkPermissions()
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun initAds()
    {
        if (sharedPreference.getValueBoolien(Const.firstLoginAd, true))
        {
            GDPRChecker()
                .withContext(this)
                .withPrivacyUrl(getString(R.string.privacy_policy_string))
                .withPublisherIds(getString(R.string.admob_pubisher_id_string))
                .check()

            sharedPreference.save(Const.firstLoginAd, false)
        }



        if (!sharedPreference.getValueBoolien(Const.showAds, true))
        {
            val date                = Calendar.getInstance().time
            val currentDateInString = date.toString("yyyy/MM/dd HH:mm:ss")

            val startDateInString   = sharedPreference.getValueString(Const.start_day)

            val format =  SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

            val currentDate         = format.parse(currentDateInString)
            val startDate           = format.parse(startDateInString)

            val currentDateInMills  = currentDate.time
            val startDateInMills    = startDate.time

            var numOfDays = ((currentDateInMills - startDateInMills ) / (1000 * 60 * 60 * 24)).toInt()



            if (numOfDays < 0)
            {
                numOfDays = numOfDays *-1
            }

            //println("timess " + numOfDays)
            //println("timess " + currentDateInString)
            //println("timess " + startDateInString)
            //println("timess " + sharedPreference.getValueInt(Const.days))
            //println("timess " + (numOfDays - sharedPreference.getValueInt(Const.days)))

            if (numOfDays - sharedPreference.getValueInt(Const.days) == 0)
            {
                sharedPreference.save(Const.showAds, true)
                initAds()
            }
            else
            {
                mAdView.visibility = View.GONE
            }
        }
        else
        {
            StartAppSDK.init(this, getString(R.string.startapp_id_string),
                false)

            adslist.add("0")

            val builder = AdRequest.Builder()
            val request = GDPRChecker.getRequest()
            if (request == GDPRChecker.Request.NON_PERSONALIZED)
            {
                var extras =  Bundle()
                extras.putString("npa", "1")
                builder.addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            }
            mAdViewBanner.loadAd(builder.build())
            mAdViewBanner.adListener = object: AdListener() {
                override fun onAdLoaded() {
                    mAdViewLargeBanner.visibility       = View.GONE
                    mAdViewMediumRectangular.visibility = View.GONE
                    mAdViewFullBanner.visibility        = View.GONE
                    mAdViewLeaderBoard.visibility       = View.GONE
                    mAdViewSmartBanner.visibility       = View.GONE

                    if (adslist.size == 0 && sharedPreference.getValueBoolien(Const.showAds, true))
                    {
                        mAdViewBanner.visibility            = View.VISIBLE
                        adslist.add("0")
                    }
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    // Code to be executed when an ad request fails.
                    mAdViewBanner.visibility = View.GONE
                    adslist.remove("0")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }

            mAdViewLargeBanner.loadAd(builder.build())
            mAdViewLargeBanner.adListener = object: AdListener() {
                override fun onAdLoaded() {
                    mAdViewMediumRectangular.visibility = View.GONE
                    mAdViewFullBanner.visibility        = View.GONE
                    mAdViewLeaderBoard.visibility       = View.GONE
                    mAdViewSmartBanner.visibility       = View.GONE

                    if (adslist.size == 0 && sharedPreference.getValueBoolien(Const.showAds, true))
                    {
                        mAdViewLargeBanner.visibility            = View.VISIBLE
                        adslist.add("1")
                    }
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    // Code to be executed when an ad request fails.
                    mAdViewLargeBanner.visibility = View.GONE
                    adslist.remove("1")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }

            mAdViewMediumRectangular.loadAd(builder.build())
            mAdViewMediumRectangular.adListener = object: AdListener() {
                override fun onAdLoaded() {
                    mAdViewLargeBanner.visibility       = View.GONE
                    mAdViewFullBanner.visibility        = View.GONE
                    mAdViewLeaderBoard.visibility       = View.GONE
                    mAdViewSmartBanner.visibility       = View.GONE

                    if (adslist.size == 0 && sharedPreference.getValueBoolien(Const.showAds, true))
                    {
                        mAdViewMediumRectangular.visibility            = View.VISIBLE
                        adslist.add("2")
                    }
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    // Code to be executed when an ad request fails.
                    mAdViewMediumRectangular.visibility = View.GONE
                    adslist.remove("2")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }

            if (resources.getBoolean(R.bool.isTablet))
            {
                mAdViewFullBanner.loadAd(builder.build())
                mAdViewFullBanner.adListener = object: AdListener() {
                    override fun onAdLoaded() {
                        mAdViewLargeBanner.visibility       = View.GONE
                        mAdViewMediumRectangular.visibility = View.GONE
                        mAdViewLeaderBoard.visibility       = View.GONE
                        mAdViewSmartBanner.visibility       = View.GONE

                        if (adslist.size == 0 && sharedPreference.getValueBoolien(Const.showAds, true))
                        {
                            mAdViewFullBanner.visibility            = View.VISIBLE
                            adslist.add("3")
                        }
                    }

                    override fun onAdFailedToLoad(errorCode: Int) {
                        // Code to be executed when an ad request fails.
                        mAdViewFullBanner.visibility = View.GONE
                        adslist.remove("3")
                    }

                    override fun onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }

                    override fun onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    override fun onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    override fun onAdClosed() {
                        // Code to be executed when the user is about to return
                        // to the app after tapping on an ad.
                    }
                }

                mAdViewLeaderBoard.loadAd(builder.build())
                mAdViewLeaderBoard.adListener = object: AdListener() {
                    override fun onAdLoaded() {
                        mAdViewLargeBanner.visibility       = View.GONE
                        mAdViewMediumRectangular.visibility = View.GONE
                        mAdViewFullBanner.visibility        = View.GONE
                        mAdViewSmartBanner.visibility       = View.GONE

                        if (adslist.size == 0 && sharedPreference.getValueBoolien(Const.showAds, true))
                        {
                            mAdViewLeaderBoard.visibility            = View.VISIBLE
                            adslist.add("4")
                        }
                    }

                    override fun onAdFailedToLoad(errorCode: Int) {
                        // Code to be executed when an ad request fails.
                        mAdViewLeaderBoard.visibility = View.GONE
                        adslist.remove("4")
                    }

                    override fun onAdOpened() {
                        // Code to be executed when an ad opens an overlay that
                        // covers the screen.
                    }

                    override fun onAdClicked() {
                        // Code to be executed when the user clicks on an ad.
                    }

                    override fun onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    override fun onAdClosed() {
                        // Code to be executed when the user is about to return
                        // to the app after tapping on an ad.
                    }
                }
            }

            mAdViewSmartBanner.loadAd(builder.build())
            mAdViewSmartBanner.adListener = object: AdListener() {
                override fun onAdLoaded() {
                    mAdViewLargeBanner.visibility       = View.GONE
                    mAdViewMediumRectangular.visibility = View.GONE
                    mAdViewFullBanner.visibility        = View.GONE
                    mAdViewLeaderBoard.visibility       = View.GONE

                    if (adslist.size == 0 && sharedPreference.getValueBoolien(Const.showAds, true))
                    {
                        mAdViewSmartBanner.visibility            = View.VISIBLE
                        adslist.add("5")
                    }
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    // Code to be executed when an ad request fails.
                    mAdViewSmartBanner.visibility = View.GONE
                    adslist.remove("5")
                }

                override fun onAdOpened() {
                    // Code to be executed when an ad opens an overlay that
                    // covers the screen.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the user is about to return
                    // to the app after tapping on an ad.
                }
            }


            mInterstitialAd = InterstitialAd(this)
            mInterstitialAd.adUnitId = getString(R.string.admob_inter_unit_string)
            mInterstitialAd.loadAd(builder.build())
            mInterstitialAd.adListener = object: AdListener() {
                override fun onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    // Code to be executed when an ad request fails.
                }

                override fun onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                override fun onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                override fun onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                override fun onAdClosed() {
                    // Code to be executed when the interstitial ad is closed.
                    mInterstitialAd.loadAd(AdRequest.Builder().build())
                }
            }


            loadReward()
        }
    }

    fun initToolbar()
    {
        setSupportActionBar(toolbar)
        toolbar.subtitle = getString(R.string.my_points_string) + " " +
                sharedPreference.getValueInt(Const.points)

        if (sharedPreference.getValueInt(Const.points_not) > 0)
        {
            addPoints(sharedPreference.getValueInt(Const.points_not), true)
        }
    }

    fun initStickSwitch()
    {
        mStickyType.onSelectedChangeListener = object : StickySwitch.OnSelectedChangeListener {

            override fun onSelectedChange(direction: StickySwitch.Direction, text: String) {

                if (direction != StickySwitch.Direction.LEFT)
                {
                    mStickyType.setLeftIcon(R.drawable.ic_image_green)
                    mStickyType.setRightIcon(R.drawable.ic_video)
                    Tpos = 1
                }
                else
                {
                    mStickyType.setLeftIcon(R.drawable.ic_image)
                    mStickyType.setRightIcon(R.drawable.ic_video_green)
                    Tpos = 0
                }


                if (pos == 0)
                {
                    RecentFragment.type = Tpos
                    RecentFragment.loadMedia()
                }
                else
                {
                    SavedFragment.type = Tpos
                    SavedFragment.loadMedia()
                }

            }
        }
    }

    private fun initRecycleViewAd() {
        mRecyclerView!!.setHasFixedSize(true)

        mLinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
            false)
        mRecyclerView!!.layoutManager = mLinearLayoutManager
        mRecyclerView!!.itemAnimator  = DefaultItemAnimator()

        var titles  = resources.getStringArray(R.array.ads_titls_array)
        var detials = resources.getStringArray(R.array.ads_detials_array)
        var urls    = resources.getStringArray(R.array.ads_urls_array)

        mAds = ArrayList()
        for (i in 0 until detials.size step 1)
        {
            mAds!!.add(AdModel(
                MyData.id_[i],
                titles[i],
                MyData.drawableArray[i],
                detials[i],
                urls[i]
            ))
        }

        mAdAdapter = AdAdapter(this@MainActivity, mAds)
        mRecyclerView!!.adapter = mAdAdapter

        mHandler = Handler()
        mRunnable = Runnable {
            if (mCurrentPosition == 0) {
                mCurrentPosition = 1
            } else {
                mCurrentPosition -= 1
            }

            mRecyclerView!!.smoothScrollToPosition(mCurrentPosition)

            mHandler!!.postDelayed(mRunnable!!, 30000)
        }
        startSlideAds()
    }

    fun initBottomNavigation()
    {
        bottomNavigation.disableShiftMode(true)
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun initRateApp()
    {
        AppRate.with(this)
            .setDebug(false)
            .setInstallDays(3)
            .setLaunchTimes(10)
            .setRemindInterval(3)
            .monitor()

        AppRate.showRateDialogIfMeetsConditions(this)
    }

    fun checkPermissions()
    {
        val permissionlistener = object : PermissionListener {

            override fun onPermissionGranted() {

                createFolder()

                loadFrag(true,true, HomeFragment.TAG, HomeFragment.newInstance(),
                    getString(R.string.home_string), R.drawable.ic_home)

            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                toast(getString(R.string.permission_denied_string), true)

            }
        }

        TedPermission.with(this)
            .setPermissionListener(permissionlistener)
            .setRationaleMessage(getString(R.string.permission_body_string))
            .setDeniedMessage(getString(R.string.permission_denied_string))
            .setGotoSettingButtonText(getString(R.string.setting_string))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    fun createFolder()
    {
        val mBaseFolderPath = getExternalFilesDir(getString(R.string.folder_name_string))

        if (sharedPreference.getValueString(Const.saveFolder) == null)
        {
            mBaseFolderPath!!.mkdirs()
            sharedPreference.save(Const.saveFolder, mBaseFolderPath.absolutePath)
        }
    }

    fun loadReward()
    {
        val builder = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        val request = GDPRChecker.getRequest()
        if (request == GDPRChecker.Request.NON_PERSONALIZED)
        {
            var extras =  Bundle()
            extras.putString("npa", "1")
            builder.addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
        }
        mRewardedAd = RewardedAd(
            this,
            getString(R.string.admob_reward_unit_string)
        )
        val adLoadCallback = object : RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {

            }

            override fun onRewardedAdFailedToLoad(errorCode: Int) {

            }
        }
        mRewardedAd.loadAd(builder.build(), adLoadCallback)
    }

    override fun showInter()
    {
        if (sharedPreference.getValueBoolien(Const.showAds, true))
        {
            if (mInterstitialAd.isLoaded)
            {
                mInterstitialAd.show()
            }
        }
    }

    override fun showReward()
    {
        if (mRewardedAd.isLoaded)
        {
            val adCallback = object : RewardedAdCallback() {
                override fun onRewardedAdOpened() {
                    // Ad opened.
                }

                override fun onRewardedAdClosed() {
                    loadReward()
                }

                override fun onUserEarnedReward(p0: RewardItem) {
                    addPoints(p0.amount)
                }

                override fun onRewardedAdFailedToShow(errorCode: Int) {
                    // Ad failed to display
                }
            }
            mRewardedAd.show(this, adCallback)

        }
    }

    fun stopSlideAds() {
        mHandler!!.removeCallbacks(mRunnable!!)
    }

    fun startSlideAds() {
        mHandler!!.post(mRunnable!!) //wait 0 ms and run
    }

    fun loadFrag(showSwitch: Boolean, add: Boolean, tag: String, fragment: Fragment,
                 title: String, icon: Int)
    {
        if (showSwitch)
        {
            mStickyType.visibility = View.VISIBLE
        }
        else
        {
            mStickyType.visibility = View.INVISIBLE
        }

        if (add)
        {
            bottomNavigation.visibility = View.VISIBLE

            toolbar.setNavigationIcon(icon)

            en_back    = false

            //    mMenu!!.setGroupVisible(R.id.my_move, true)

            loadFragment(add, tag) {
                replace(R.id.frmHomeContainer, fragment, tag)
            }
        }
        else
        {
            bottomNavigation.visibility = View.GONE

            toolbar.setNavigationIcon(R.drawable.ic_back)

            en_back    = true
            //    mMenu!!.setGroupVisible(R.id.my_move, false)

            loadFragment(add, tag) {
                replace(R.id.frmHomeContainer, fragment)
            }
        }

        fragCounts += 1
        addTags(tag)
        //supportActionBar!!.setIcon(icon)
        toolbar.title = title
    }

    fun addTags(tag: String)
    {
        list.remove(tag)

        list.add(tag)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    fun shareApp()
    {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            getString(R.string.check_out_string) + " https://play.google.com/store/apps/details?id=" +
        packageName)
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {


            R.id.share_item -> {

                shareApp()
                addPoints(10)

                return true
            }

            R.id.policy_privacy_item -> {

                loadFrag(false, false, PolicyPrivacyFragment.TAG, PolicyPrivacyFragment.newInstance(),
                    getString(R.string.policy_privacy_string), R.drawable.ic_home)
                addPoints(10)
                showInter()

                return true
            }

            R.id.whatsapp_me_item -> {

                whatsappMe(getString(R.string.whatsapp_number_string))
                addPoints(10)

                return true
            }

            R.id.about_us_item -> {

                loadFrag(false,false, AboutUsFragment.TAG, AboutUsFragment.newInstance(),
                    getString(R.string.about_us_string), R.drawable.ic_home)
                addPoints(10)
                showInter()

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if (list.size > 1)
        {
            var tag  = list[list.size - 1]
            var tag1 = list[list.size - 2]

            // هنا تغير التاب إذا يحتاج
            when (tag1.toInt())
            {
                0 -> {

                    bottomNavigation.selectedItemId = R.id.navigation_home

                    loadFragment(false, tag) {
                        replace(R.id.frmHomeContainer, HomeFragment.newInstance())
                    }

                    removeFragmentByTag("" + tag )
                    supportFragmentManager.popBackStack("" + tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    list.remove(tag)

                    return

                }



                1 -> {

                    bottomNavigation.selectedItemId = R.id.navigation_points

                }

                2 -> {

                    bottomNavigation.selectedItemId = R.id.navigation_settings

                }

            }

            loadFragment(true, "" + tag1) {

                if (tag1.equals("0"))
                {
                    var fragment = HomeFragment.newInstance()

                    replace(R.id.frmHomeContainer, fragment, "" + HomeFragment.TAG)
                }
                else
                {
                    var fragment = findFragmentByTag("" + tag1)

                    if (fragment == null)
                        fragment = SettingsFragment.newInstance()

                    replace(R.id.frmHomeContainer, fragment, "" + tag1)
                }


            }

            removeFragmentByTag("" + tag )
            supportFragmentManager.popBackStack("" + tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            list.remove(tag)
        }
        else
        {
            if (sharedPreference.getValueBoolien(Const.firstLoginAd, true))
            {
                StartAppAd.onBackPressed(this)
            }

            finish()
        }
    }

    override fun addPoints(points: Int, isFromNotification: Boolean)
    {
        userViewModel.updatePoints(sharedPreference.getValueInt(Const.id), points)
            .observe(this@MainActivity, Observer {
                when (it?.status) {
                    Status.SUCCESS -> {


                        println("steeeeep 1")

                        sharedPreference.save(Const.points,
                            sharedPreference.getValueInt(Const.points) + points)



                        toolbar.subtitle = getString(R.string.my_points_string) + " " +
                                sharedPreference.getValueInt(Const.points)

                        if (points < 0)
                        {
                            sharedPreference.save(Const.showAds, false)
                            initAds()

                        }

                        if (isFromNotification)
                        {
                            sharedPreference.save(Const.points_not, 0)
                            sharedPreference.save(Const.days, 0)
                        }

                        //             countingIdleResources.decrement()
                    }
                    Status.ERROR -> {

                        println("steeeeep 2")

                        toast(getString(R.string.need_internet_string),
                            true)

                    }
                    Status.LOADING -> {



                        Timber.e("Loading")

                    }
                }
            })
    }

    public override fun onResume() {

        startSlideAds()
        super.onResume()
    }

    public override fun onPause() {

        stopSlideAds()
        super.onPause()
    }

    public override fun onDestroy() {

        stopSlideAds()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {

        if (en_back)
            onBackPressed()

        return super.onSupportNavigateUp()
    }
}
