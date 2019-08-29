package com.bbalabsi.whatsaver.fragment.home


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.bbalabsi.base.loadFromUrl
import com.bbalabsi.whatsaver.BuildConfig
import com.bbalabsi.whatsaver.R
import com.bbalabsi.whatsaver.activity.main.ReplaceFragmentListener
import com.bbalabsi.whatsaver.utils.Const
import com.bbalabsi.whatsaver.utils.SharedPreference
import com.bbalabsi.whatsaver.utils.SquareImageView
import com.bbalabsi.whatsaver.utils.getMimeType
import com.sackcentury.shinebuttonlib.ShineButton
import java.io.File
import java.util.*


class StatusAdapter
    (
    private val mActivity:               Activity,
    private val mContext:                Context,
    private val mStatus:                 ArrayList<String>,
    private val isSaved:                 Boolean,
    private val replaceFragmentListener: ReplaceFragmentListener
) : RecyclerView.Adapter<StatusAdapter.ViewHolder>() {


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        open val mImagePlayer: ImageView
        open val mStatusImage: SquareImageView
        open val mPlayIB:      ShineButton
        open val mDownloadIB:  ShineButton
        open val mRemoveIB:    ShineButton
        open val mShareIB:     ShineButton

        private val mCV: CardView

        init {
            mStatusImage = v.findViewById<View>(R.id.imageView)   as SquareImageView
            mImagePlayer = v.findViewById<View>(R.id.iconplayer)  as ImageView
            mPlayIB      = v.findViewById<View>(R.id.play_ib)     as ShineButton
            mDownloadIB  = v.findViewById<View>(R.id.download_ib) as ShineButton
            mRemoveIB    = v.findViewById<View>(R.id.remove_ib)   as ShineButton
            mShareIB     = v.findViewById<View>(R.id.share_ib)    as ShineButton
            mCV          = v.findViewById<View>(R.id.card_view)   as CardView


            mCV.setOnClickListener(this)
            mPlayIB.setOnClickListener(this)
            mDownloadIB.setOnClickListener(this)
            mRemoveIB.setOnClickListener(this)
            mImagePlayer.setOnClickListener(this)
            mShareIB.setOnClickListener(this)

            if (isSaved)
            {
                mShareIB.visibility = View.VISIBLE
            }
            else
            {
                mDownloadIB.visibility = View.VISIBLE
            }

        }

        override fun onClick(v: View)
        {
            val mStatus = mStatus[adapterPosition]

            when (v.id)
            {
                R.id.iconplayer, R.id.play_ib, R.id.card_view -> {

                    play(mStatus, getMimeType(File(mStatus)))

                    replaceFragmentListener.showInter()
                    replaceFragmentListener.addPoints(5)

                }

                R.id.download_ib -> {

                    download(File(mStatus), adapterPosition)

                    replaceFragmentListener.showInter()
                    replaceFragmentListener.addPoints(5)

                }

                R.id.remove_ib -> {

                    remove(File(mStatus), adapterPosition)

                    replaceFragmentListener.showInter()
                    replaceFragmentListener.addPoints(5)
                }

                R.id.share_ib -> {

                    shareStatus(mStatus, getMimeType(File(mStatus)))

                    replaceFragmentListener.showInter()
                    replaceFragmentListener.addPoints(5)
                }
            }
        }
    }

    fun play (filepath: String, type: String)
    {
        val uri = FileProvider.getUriForFile(
            mActivity,
            BuildConfig.APPLICATION_ID + ".provider",
            File(filepath)
        )

        val intent = Intent(Intent.ACTION_VIEW)

        intent.setDataAndType(uri, type)
        intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        mActivity.startActivity(intent)
    }

    fun remove (file: File, adapterPosition: Int)
    {
        if (file.exists())
        {
            file.delete()
        }

        mStatus.remove(file.absolutePath)

        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition, mStatus.size)
    }

    fun download(file: File, adapterPosition: Int)
    {
        val sharedPreference = SharedPreference(mActivity)

        file.copyTo(File(sharedPreference.getValueString(Const.saveFolder) + "/" + file.name),
            true)

        mStatus.remove(file.absolutePath)

        notifyItemRemoved(adapterPosition)
        notifyItemRangeChanged(adapterPosition, mStatus.size)
    }

    fun shareStatus(filepath: String, type: String)
    {
        val intent = Intent(Intent.ACTION_SEND, Uri.parse(filepath))
        intent.setDataAndType(
            FileProvider.getUriForFile(
                mActivity, "com.bbalabsi.whatsaver.provider",
                File(filepath)), type)
        intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(
            mActivity,
            "com.bbalabsi.whatsaver.provider", //(use your app signature + ".provider" )
            File(filepath)))
        mActivity.startActivity(Intent.createChooser(intent,
            mActivity.getString(R.string.share_using_string)))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.status_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val mStatus = mStatus[position]

        holder.mStatusImage.loadFromUrl(mStatus)

        if (getMimeType(File(mStatus)).contains("image"))
        {
            holder.mImagePlayer.visibility = View.INVISIBLE
        }
        else if (getMimeType(File(mStatus)).contains("audio"))
        {
            holder.mImagePlayer.visibility = View.INVISIBLE
        }
        else
        {
            holder.mImagePlayer.visibility = View.VISIBLE
        }
    }


    override fun getItemCount(): Int {
        return mStatus.size
    }

}
