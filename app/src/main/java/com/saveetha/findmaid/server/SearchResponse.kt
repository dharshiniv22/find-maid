package com.saveetha.findmaid.server

data class SearchResponse(
    val status: Boolean,
    val message: String?,
    val data: List<Maid>
)

data class Maid(
    val id: Int,
    val name: String,
    val age: Int,
    val location: String,
    val category: String,
    val expected_salary: Double,
    val working_hours: String,
    val photo_url: String?
)
