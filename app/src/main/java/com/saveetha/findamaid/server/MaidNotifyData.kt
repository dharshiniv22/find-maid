package com.saveetha.findamaid.server

data class MaidNotifyDataModel(
    val request_id: Int,
    val address: String,
    val pincode: String,
    val status: String,
    val requested_at: String,
    val notifyBooker: NotifyBooker,
    val notifyMaidPost: NotifyMaidPost,
    val notifyMaidOwner: NotifyMaidOwner
)

data class NotifyBooker(
    val name: String,
    val photo: String?
)

data class NotifyMaidPost(
    val id: Int,
    val name: String,
    val photo: String?,
    val location: String,
    val expected_salary: String,
    val working_hours: String,
    val category: String
)

data class NotifyMaidOwner(
    val name: String,
    val photo: String?
)
