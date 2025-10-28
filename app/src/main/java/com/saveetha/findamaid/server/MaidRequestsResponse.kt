package com.saveetha.findamaid.server

data class MaidRequestsResponse(
    val status: Boolean,
    val role: String?,
    val user_id: Int?,
    val total: Int?,
    val notifications: List<MaidRequestData>?
)


data class StatusResponse(
    val status: Boolean,
    val message: String
)
