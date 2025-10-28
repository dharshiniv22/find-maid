package com.saveetha.findamaid.server

data class UserRequest(
    val request_id: Int,
    val address: String,
    val pincode: String,
    val status: String,
    val requested_at: String,
    val maid_post: MaidPost,
    val booker: Booker? = null,
    val maid_owner: MaidOwner? = null
)

data class MaidPost(
    val id: Int,
    val name: String,
    val photo: String?,
    val location: String,
    val expected_salary: String,
    val working_hours: String,
    val category: String
)

data class Booker(
    val name: String,
    val photo: String?
)

data class MaidOwner(
    val name: String,
    val photo: String?
)
