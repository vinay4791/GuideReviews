package com.example.guidereviews.reviews

import com.example.guidereviews.api.ErrorType
import com.example.guidereviews.reviews.backend.ListApiFetcher
import com.example.guidereviews.reviews.backend.ListResponse
import com.example.guidereviews.reviews.viewstate.ReviewListConverter
import com.example.guidereviews.reviews.viewstate.ReviewListViewState
import com.example.guidereviews.rx.TestSchedulingStrategyFactory
import com.nhaarman.mockitokotlin2.whenever
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""

@RunWith(MockitoJUnitRunner::class)
class ReviewsListRepositoryTest {

    @Mock
    private lateinit var listApiFetcher: ListApiFetcher

    @Mock
    private lateinit var reviewListConverter: ReviewListConverter

    @Mock
    private lateinit var reviewListViewState: ReviewListViewState

    @Mock
    private lateinit var listResponse: ListResponse

    private lateinit var repository: ReviewsListRepository

    @Before
    fun setUp() {
        repository = ReviewsListRepository(
            listApiFetcher,
            reviewListConverter,
            TestSchedulingStrategyFactory.immediate())
    }

    @Test
    fun `should return correct movie view state when fetchReviewListDetails API is called`() {
        whenever(listApiFetcher.fetchReviewListDetails(EMPTY_STRING, EMPTY_STRING)).thenReturn(
            Single.just(listResponse))
        whenever(reviewListConverter.apply(listResponse)).thenReturn(reviewListViewState)
        val observer = repository.fetchReviewList(EMPTY_STRING, EMPTY_STRING).test()
        observer.assertValues(ReviewListViewState.Loading, reviewListViewState)
    }

    @Test
    fun `should return error viewState while  fetching  reviews`() {
        whenever(listApiFetcher.fetchReviewListDetails(EMPTY_STRING, EMPTY_STRING)).thenReturn(Single.error(
            JsonEncodingException("json error")
        ))
        val testObserver = repository
            .fetchReviewList(EMPTY_STRING, EMPTY_STRING)
            .test()
        val errorViewState = ReviewListViewState.Error(ErrorType.UNKNOWN)
        testObserver.assertValues(ReviewListViewState.Loading, errorViewState)
    }



}