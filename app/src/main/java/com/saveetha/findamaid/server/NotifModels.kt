package com.saveetha.findamaid.server

data class NotifModels(
    val request_id: Int,
    val address: String,
    val pincode: String,
    val status: String,
    val requested_at: String,
    val maid_post: NotifMaidPost,
    val booker: NotifBooker?
)

data class NotifMaidPost(
    val id: Int,
    val name: String,
    val age: String,
    val education: String,
    val experience: String,
    val contact: String,
    val salary: String,
    val work_time: String,
    val category: String,
    val image_url: String
)

data class NotifBooker(
    val id: Int,
    val name: String,
    val phone: String
)
