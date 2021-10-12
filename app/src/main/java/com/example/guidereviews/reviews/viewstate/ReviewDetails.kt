package com.example.guidereviews.reviews.viewstate

import com.example.guidereviews.util.AppConstants.EMPTY_DOUBLE
import com.example.guidereviews.util.AppConstants.EMPTY_INT


data class ReviewDetails(
    val reviewItemList: List<ReviewItem>,
    val totalCount: Int,
    val averageRating: Double,
    val pagination: Pagination
) {
    companion object {
        val EMPTY = ReviewDetails(
            reviewItemList = listOf(ReviewItem.EMPTY),
            totalCount = EMPTY_INT,
            averageRating = EMPTY_DOUBLE,
            pagination = Pagination.EMPTY
        )
    }
}