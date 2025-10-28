package com.saveetha.findamaid.server

data class MaidDetailResponse(
    val status: Boolean,
    val maid: MaidDetail?,
    val message: String? = null
)

data class MaidDetail(
    val id: Int,
    val name: String?,
    val age: String?,
    val location: String?,
    val experience: String?,
    val language: String?,
    val phone_number: String?,
    val expected_salary: String?,
    val working_hours: String?,
    val category: String?,
    val photo_url: String?
)
