package com.saveetha.findmaid.server

data class MaidRequest(
    val id: Int,
    val user_id: Int,
    val name: String,
    val age: Int,
    val location: String,
    val experience: String,
    val phone_number: String,
    val expected_salary: String,
    val working_hours: String,
    val photo: String,
    val status: String
)
