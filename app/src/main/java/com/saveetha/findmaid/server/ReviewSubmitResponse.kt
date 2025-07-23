package com.saveetha.findmaid.server

    data class ReviewSubmitResponse(
        val status: String,
        val message: String
    )
    data class Review(
    val reviewerName: String,
    val reviewText: String,
    val starRating: Int
   )