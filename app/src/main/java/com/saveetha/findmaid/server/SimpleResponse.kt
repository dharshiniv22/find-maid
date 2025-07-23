package com.saveetha.findmaid.server

data class SimpleResponse(
    val status: Boolean,
    val message: String,
    val request_id: Int? = null
)

