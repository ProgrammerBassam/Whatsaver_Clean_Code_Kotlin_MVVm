package com.bbalabsi.whatsaver.fragment.home.recent_fragment


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bbalabsi.base.BaseFragment
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.MainActivity
import com.bbalabsi.whatsaver.activity.main.ReplaceFragmentListener
import com.bbalabsi.whatsaver.fragment.home.StatusAdapter
import com.bbalabsi.whatsaver.utils.Const
import com.bbalabsi.whatsaver.utils.SharedPreference
import com.bbalabsi.whatsaver.utils.getMimeType
import com.maciejkozlowski.fragmentutils_kt.getListenerOrThrowException
import kotlinx.android.synthetic.main.fragment_status.*
import java.io.File


class RecentFragment @SuppressLint("ValidFragment")
private constructor() : BaseFragment() {

    private lateinit var sharedPreference: SharedPreference

    private var mLayoutManager: RecyclerView.LayoutManager? = null


    override fun getLayoutId() = R.layout.fragment_status

    private lateinit var replaceFragmentListener: ReplaceFragmentListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        replaceFragmentListener = getListenerOrThrowException(ReplaceFragmentListener::class.java)
    }

    companion object {
        const val TAG  = "5"

        var mContext:                 Context?                 = null
        var mActivity:                Activity?                = null
        var mSStatusRV:               RecyclerView?            = null
        var mReplaceFragmentListener: ReplaceFragmentListener? = null

        var type = 0

        var mRecentStatus = ArrayList<String>()

        fun newInstance() = RecentFragment()

        fun loadMedia()
        {
            var sharedPreference = SharedPreference(mContext!!)

            val normalWhatsApp    = File(
                        Environment.getExternalStorageDirectory().absolutePath
                                + File.separator + "WhatsApp/Media/.Statuses" + File.separator)

            mRecentStatus.clear()

            normalWhatsApp.walk().forEach {

                val savedWhatsApp    = File(sharedPreference
                    .getValueString(Const.saveFolder) + "/" + it.name)

                if (!savedWhatsApp.exists())
                {
                    if (type == 0)
                    {
                        if (!it.isDirectory && getMimeType(it).contains("image"))
                        {
                            mRecentStatus.add(it.absolutePath)
                        }
                    }
                    else
                    {
                        if (!it.isDirectory && getMimeType(it).contains("video"))
                        {
                            mRecentStatus.add(it.absolutePath)
                        }
                        else if (!it.isDirectory && getMimeType(it).contains("audio"))
                        {
                            mRecentStatus.add(it.absolutePath)
                        }
                    }
                }
            }

            val mStatusAdapter = StatusAdapter(mActivity!!, mContext!!, mRecentStatus, false,
                mReplaceFragmentListener!!)
            mSStatusRV!!.adapter = mStatusAdapter
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        sharedPreference = SharedPreference(context!!)

        mContext = context!!

        mActivity = activity

        mReplaceFragmentListener = replaceFragmentListener
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRV()
        loadMedia()
    }

    fun initRV()
    {
        mStatusRV.setHasFixedSize(true)
        mLayoutManager = GridLayoutManager(context, 2)
        mStatusRV.layoutManager = mLayoutManager
        type = MainActivity.Tpos

        mSStatusRV = mStatusRV
    }
}