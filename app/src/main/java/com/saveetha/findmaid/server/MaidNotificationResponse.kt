package com.saveetha.findmaid.server

data class MaidNotificationResponse(
    val status: Boolean,
    val role: String?, // "maid" or "user"
    val user_id: Int,
    val total: Int,
    val notifications: List<MaidRequestData>?, // For maids
    val bookings: List<MaidRequestData>?       // For users
)

data class MaidRequestData(
    val request_id: Int,
    val address: String,
    val pincode: String,
    val status: String,
    val requested_at: String,
    val booker: Booker,
    val maid_post: MaidPost,
    val maid_owner: MaidOwner
)

data class Booker(
    val name: String,
    val photo: String?
)

data class MaidPost(
    val id: Int,
    val name: String,
    val photo: String?,
    val location: String,
    val expected_salary: String,
    val working_hours: String,
    val category: String
)

data class MaidOwner(
    val name: String,
    val photo: String?
)
