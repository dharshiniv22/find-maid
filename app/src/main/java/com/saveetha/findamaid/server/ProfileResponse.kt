package com.saveetha.findamaid.server

data class ProfileResponse(
    val success: Boolean,
    val data: ProfileData?
)


data class ProfileData(
    val user_id: Int,       // ✅ Must match PHP response
    val name: String?,
    val email: String?,
    val contact_number: String?,
    val profile_image: String?,
    val username: String?
)

