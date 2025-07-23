package com.saveetha.findmaid.server

import com.saveetha.findmaid.model.Review

data class ReviewResponse(
    val status: String,
    val reviews: List<Review>
)
