package com.saveetha.findmaid.server

data class MaidDetailResponse(
    val status: Boolean,
    val maid: MaidDetail
)

data class MaidDetail(
    val id: Int,
    val name: String,
    val age: Int,
    val location: String,
    val experience: String,
    val phone_number: String,
    val expected_salary: Double,
    val working_hours: String,
    val category: String,
    val photo_url: String?
)
