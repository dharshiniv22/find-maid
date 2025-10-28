package com.saveetha.findamaid.server

data class ServerNotification(
    val request_id: Int,
    val address: String,
    val pincode: String,
    val status: String,
    val requested_at: String,
    val maid_post: ServerMaid,
    val booker: ServerBooker?,        // Only present if role = maid
    val maid_owner: ServerMaidOwner?  // Only present if role = user
)

data class ServersMaid(
    val id: Int,
    val name: String,
    val photo: String?,
    val location: String,
    val expected_salary: String,
    val working_hours: String,
    val category: String
)

data class ServerBooker(
    val name: String,
    val photo: String?
)

data class ServerMaidOwner(
    val name: String,
    val photo: String?
)
