package com.saveetha.findamaid.server

data class kNotificationServer(
    val id: Int,
    val requester_id: Int,
    val name: String,
    val phone_number: String,       // ✅ add phone number
    val maid_post_id: Int,
    val address: String,
    val pincode: String,
    val preferred_time: String?,
    val notes: String?,
    var status: String,             // mutable to update Accept/Reject
    val latitude: String?,
    val longitude: String?,
    val maid_user_id: Int,
    val maid_name: String,
    val maid_category: String,      // ✅ maid category
    val maid_location: String,
    val photo_full_url: String
)

data class NotificationsResponse(
    val status: Boolean,
    val message: String,
    val data: List<kNotificationServer>?
)
