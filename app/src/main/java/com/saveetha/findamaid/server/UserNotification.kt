//package com.saveetha.findmaid.server
//
//data class MaidNotificationResponse(
//    val status: Boolean,
//    val owner_id: String,
//    val total_bookings: Int,
//    val bookings: List<MaidRequestData>?
//)
//
//data class MaidRequestData(
//    val request_id: Int,
//    val address: String,
//    val pincode: String,
//    val status: String,
//    val requested_at: String,
//    val maid_post: MaidPost,
//    val booker: Booker?,         // For maid users
//    val maid_owner: MaidOwner?   // For regular users
//)
//
//data class MaidPost(
//    val id: Int,
//    val name: String,
//    val photo: String?,
//    val location: String,
//    val expected_salary: String,
//    val working_hours: String,
//    val category: String
//)
//
//data class Booker(
//    val name: String,
//    val photo: String?
//)
//
//data class MaidOwner(
//    val name: String,
//    val photo: String?
//)
