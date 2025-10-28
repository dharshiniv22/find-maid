package com.saveetha.findamaid.server

data class MaidNotificationResponse(
    val status: Boolean,
    val role: String,
    val user_id: Int,
    val total: Int,
    val notifications: List<MaidRequestData>
)

data class MaidRequestData(
    val request_id: Int,
    val address: String,
    val pincode: String,
    val status: String,
    val requested_at: String,
    val maid_post: MaidPost,
    val booker: Booker? = null,      // present if role = maid
    val maid_owner: MaidOwner? = null // present if role = user
)

