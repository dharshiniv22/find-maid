package com.saveetha.findamaid.server

import com.google.gson.annotations.SerializedName

data class NotificationItem(
    @SerializedName("requestId")
    val id: Int,

    @SerializedName("maidName")
    val maidName: String? = "",

    @SerializedName("category")
    val category: String? = "",

    @SerializedName("requestDate")
    val requestDate: String? = "",

    @SerializedName("status")
    val status: String? = "",

    @SerializedName("photo_url") // <-- updated to match PHP JSON
    val photo_url: String? = null
)
