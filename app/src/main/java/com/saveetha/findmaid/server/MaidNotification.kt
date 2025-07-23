package com.saveetha.findmaid.server

data class MaidNotification(
    val request_id: Int,
    val maid_name: String,
    val category: String,
    val address: String,
    val pincode: String,
    val status: String,
    val maid_photo: String?,
    val requested_at: String
)

