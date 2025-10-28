package com.saveetha.findamaid.server

data class ServerMaid(
    val id: Int,
    val name: String,
    val maid_id: Int,
    val age: Int,
    val location: String?,
    val category: String,
    val expected_salary: String,
    val working_hours: String,
    val photo_url: String?
)
