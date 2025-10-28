package com.saveetha.findamaid.server

data class SignupResponse(
    val status: Boolean,
    val message: String,
    val user: SignupUser?
)

data class SignupUser(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val contact_number: String,
    val profile_image: String?
)
