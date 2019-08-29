package com.bbalabsi.whatsaver.fragment.points

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import com.bbalabsi.base.BaseFragment
import com.bbalabsi.base.toast
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.ReplaceFragmentListener
import com.bbalabsi.whatsaver.utils.Const
import com.bbalabsi.whatsaver.utils.SharedPreference
import com.maciejkozlowski.fragmentutils_kt.getListenerOrThrowException
import kotlinx.android.synthetic.main.fragment_points.*
import java.text.SimpleDateFormat
import java.util.*

class PointsFragment @SuppressLint("ValidFragment")
private constructor() : BaseFragment() {

    override fun getLayoutId() = R.layout.fragment_points

    companion object {
        const val TAG = "1"
        fun newInstance() = PointsFragment()
    }

    private lateinit var replaceFragmentListener: ReplaceFragmentListener
    private lateinit var mSharedPreference:       SharedPreference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = getListenerOrThrowException(ReplaceFragmentListener::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        mSharedPreference = SharedPreference(context!!)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mAddPointsACB.setOnClickListener {

            replaceFragmentListener.showReward()

        }

        mRemoveAdsACB.setOnClickListener {

            if (mSharedPreference.getValueInt(Const.points) >= 300)
            {
                val times = mSharedPreference.getValueInt(Const.points) / 300

                //println("timess1 " + times)

                val days  = times * 7

                //println("timess1 " + days)

                val date = Calendar.getInstance().time
                val dateInString = date.toString("yyyy/MM/dd HH:mm:ss")

                mSharedPreference.save(Const.start_day, dateInString)
                mSharedPreference.save(Const.days, mSharedPreference.getValueInt(Const.days) + days)

                if ((mSharedPreference.getValueInt(Const.points) % 300) == 0)
                {
                    replaceFragmentListener.addPoints(mSharedPreference.getValueInt(Const.points) * -1)
                }
                else
                {
                    replaceFragmentListener.addPoints((times * 300) * -1)
                }

                toast(getString(R.string.done_string), true)
            }
            else
            {
                toast(getString(R.string.not_enough_points_string), true)
            }

        }
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }
}