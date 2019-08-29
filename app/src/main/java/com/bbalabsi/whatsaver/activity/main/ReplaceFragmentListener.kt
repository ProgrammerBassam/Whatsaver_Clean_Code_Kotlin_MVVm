package com.bbalabsi.whatsaver.activity.main

interface ReplaceFragmentListener {

    fun showReward()

    fun showInter()

    fun addPoints(points: Int, isFromNotification: Boolean = false)
}