package com.saveetha.findamaid.server

data class ServerMaidResponse(
    val status: Boolean,
    val data: List<ServerMaid>,
    val message: String
)
