package com.bbalabsi.whatsaver.activity.main

class AdModel(
    private var mID: Int,
    private var mName: String?,
    private var mImage: Int,
    private var mDetails: String?,
    private var mUrl: String?
) {

    fun getmID(): Int {
        return mID
    }

    fun setmID(mID: Int) {
        this.mID = mID
    }

    fun getmName(): String? {
        return mName
    }

    fun setmName(mName: String) {
        this.mName = mName
    }

    fun getmImage(): Int {
        return mImage
    }

    fun setmImage(mImage: Int) {
        this.mImage = mImage
    }

    fun getmDetails(): String? {
        return mDetails
    }

    fun setmDetails(mDetails: String) {
        this.mDetails = mDetails
    }

    fun getmUrl(): String? {
        return mUrl
    }

    fun setmUrl(mUrl: String) {
        this.mUrl = mUrl
    }
}