package com.bbalabsi.whatsaver.data.source

import com.bbalabsi.whatsaver.data.model.User
import com.google.gson.annotations.SerializedName

data class UserSource(
    @SerializedName("success")     var  success:      Boolean          = false,
    @SerializedName("user")        var  user:         User?            = null)