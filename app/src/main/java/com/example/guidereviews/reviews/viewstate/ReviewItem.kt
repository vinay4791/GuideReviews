package com.example.guidereviews.reviews.viewstate

import com.example.guidereviews.util.AppConstants.EMPTY_INT
import com.example.guidereviews.util.AppConstants.EMPTY_STRING

data class ReviewItem(
    val date: String,
    val rating: Int,
    val message: String,
    val authorName: String,
    val authorCountry: String,
    val authorImage: String,
    val authorAndCountry: String
) {
    companion object {
        val EMPTY = ReviewItem(
            date = EMPTY_STRING,
            rating = EMPTY_INT,
            message = EMPTY_STRING,
            authorName = EMPTY_STRING,
            authorCountry = EMPTY_STRING,
            authorImage = EMPTY_STRING,
            authorAndCountry = EMPTY_STRING
        )
    }
}

data class Pagination(
    val limit: Int,
    val offset: Int
) {
    companion object {
        val EMPTY = Pagination(
            limit = EMPTY_INT,
            offset = EMPTY_INT,
        )
    }
}