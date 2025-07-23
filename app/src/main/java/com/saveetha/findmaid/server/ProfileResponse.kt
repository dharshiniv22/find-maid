package com.saveetha.findmaid.server

data class ProfileResponse(
    val success: Boolean,
    val data: ProfileData?
)

data class ProfileData(
    val name: String?,
    val email: String?,
    val contact_number: String?,
    val profile_image: String?,
    val username: String?
)

