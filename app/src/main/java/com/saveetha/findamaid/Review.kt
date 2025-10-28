package com.saveetha.findamaid.model


data class Review(
    val id: Int,
    val maid_post_id: Int,
    val user_id: Int,
    val maid_name: String,
    val rating: Int,
    val comment: String,
    val created_at: String,
)



data class ReviewSubmitResponse(
    val status: Boolean,
    val message: String
)




