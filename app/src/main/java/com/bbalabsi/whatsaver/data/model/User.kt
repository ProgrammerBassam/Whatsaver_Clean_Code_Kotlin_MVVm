package com.bbalabsi.whatsaver.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class  User(



    @field:SerializedName("id")
    @PrimaryKey
    val id: Int,

    @field:SerializedName("user_id")
    val user_id: String,

    @field:SerializedName("points")
    val points: Int
)