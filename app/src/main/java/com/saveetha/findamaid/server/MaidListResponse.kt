package com.saveetha.findamaid.server

data class MaidListResponse(
    val status: Boolean,
    val maids: List<MaidData>
)

data class MaidData(
    val id: Int,
    val name: String,
    val maid_id: Int,
    val age: Int,
    val expected_salary: Double,
    val working_hours: String,
    val photo_url: String?
)
