package com.example.guidereviews.reviews

import com.example.guidereviews.reviews.backend.Author
import com.example.guidereviews.reviews.backend.ListResponse
import com.example.guidereviews.reviews.backend.Pagination
import com.example.guidereviews.reviews.backend.Review
import com.example.guidereviews.reviews.viewstate.ReviewDetails
import com.example.guidereviews.reviews.viewstate.ReviewListConverter
import com.example.guidereviews.reviews.viewstate.ReviewListViewState
import com.example.guidereviews.util.Utils
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""
private const val EMPTY_INT = 0
private const val EMPTY_DOUBLE = 0.0

@RunWith(MockitoJUnitRunner::class)
class ReviewsListConverterTest {

    @Mock
    private lateinit var utils: Utils

    private lateinit var reviewListConverter: ReviewListConverter

    @Before
    fun setUp() {
        reviewListConverter = ReviewListConverter(utils)
    }

    @Test
    fun `should convert list of review api response to review viewstate`() {

        whenever(utils.getFormattedDate(EMPTY_STRING)).thenReturn(EMPTY_STRING)
        whenever(utils.appendAuthorAndCountry(EMPTY_STRING,EMPTY_STRING)).thenReturn(EMPTY_STRING)

        val convertedViewState = reviewListConverter.apply(getMockReviewListResponse())
        val expected = ReviewListViewState.Success(ReviewDetails.EMPTY)
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    /**
     * Returns the mock review List response
     */
    private fun getMockReviewListResponse(): ListResponse {
        val reviewItems = mutableListOf(
            Review(
                id = EMPTY_INT,
                author = Author(
                    EMPTY_STRING,
                    EMPTY_STRING,
                    EMPTY_STRING
                ),
                title = EMPTY_STRING,
                message = EMPTY_STRING,
                isAnonymous = false,
                fullName = EMPTY_STRING,
                rating = EMPTY_INT,
                created = EMPTY_STRING,
                optionId = EMPTY_INT,
                activityId = EMPTY_INT,
                language = EMPTY_STRING,
                travelerType = EMPTY_STRING
            )
        )

        val pagination = Pagination(
            EMPTY_INT,EMPTY_INT
        )

        return ListResponse(
            reviews = reviewItems,
            pagination = pagination,
            averageRating = EMPTY_DOUBLE,
            totalCount = EMPTY_INT
        )

    }
}



