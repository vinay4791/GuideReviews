package com.example.guidereviews.reviews.viewstate

import com.example.guidereviews.reviews.backend.ListResponse
import com.example.guidereviews.rx.Converter
import com.example.guidereviews.util.AppConstants.EMPTY_DOUBLE
import com.example.guidereviews.util.AppConstants.EMPTY_INT
import com.example.guidereviews.util.AppConstants.EMPTY_STRING
import com.example.guidereviews.util.Utils

class ReviewListConverter(private val utils: Utils) :
    Converter<ListResponse, ReviewListViewState> {
    override fun apply(listResponse: ListResponse): ReviewListViewState {

        val reviewList = listResponse.reviews?.map { review ->
            ReviewItem(
                date = utils.getFormattedDate(review.created),
                rating = review.rating ?: EMPTY_INT,
                message = review.message ?: EMPTY_STRING,
                authorName = review.author?.fullName ?: EMPTY_STRING,
                authorCountry = review.author?.country ?: EMPTY_STRING,
                authorImage = review.author?.photo ?: EMPTY_STRING,
                authorAndCountry = utils.appendAuthorAndCountry(
                    review.author?.fullName ?: EMPTY_STRING,
                    review.author?.country ?: EMPTY_STRING
                )
            )
        } ?: emptyList()

        val pagination = listResponse.pagination?.let { pagination ->
            Pagination(
                pagination.limit ?: EMPTY_INT,
                pagination.offset ?: EMPTY_INT
            )
        } ?: Pagination.EMPTY

        val reviewDetails = ReviewDetails(
            reviewList,
            listResponse.totalCount ?: EMPTY_INT,
            listResponse.averageRating ?: EMPTY_DOUBLE,
            pagination
        )
        return ReviewListViewState.Success(reviewDetails)
    }

}