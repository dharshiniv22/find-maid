package com.saveetha.findamaid.server

import com.saveetha.findamaid.model.Review

data class ReviewResponse(
    val status: String,
    val reviews: List<Review>
)
