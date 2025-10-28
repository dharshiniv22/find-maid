// UserRequestData.kt
package com.saveetha.findamaid.server

data class UserRequestData(
    val id: Int,
    val maid_name: String,
    val status: String,
    val requested_at: String,
    val image_url: String
)
