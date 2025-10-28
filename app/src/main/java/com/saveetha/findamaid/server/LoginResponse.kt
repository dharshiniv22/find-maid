package com.saveetha.findamaid.server

data class LoginData(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val contact_number: String,
    val profile_image: String?
)

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val data: LoginData
)
