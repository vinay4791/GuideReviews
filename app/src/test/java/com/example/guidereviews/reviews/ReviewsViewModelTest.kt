package com.example.guidereviews.reviews

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.guidereviews.reviews.viewmodel.ReviewsViewModel
import com.example.guidereviews.reviews.viewstate.ReviewListViewState
import com.example.guidereviews.rx.testObserver
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""

@RunWith(MockitoJUnitRunner::class)
class ReviewsViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: ReviewsListRepository

    @Mock
    private lateinit var reviewsListViewState: ReviewListViewState

    private lateinit var reviewsViewModel: ReviewsViewModel

    @Before
    fun setUp() {
        reviewsViewModel = ReviewsViewModel(repository)
    }

    @Test
    fun `should emit review list view state when fetchReviewList is fetched`() {
        whenever(repository.fetchReviewList(EMPTY_STRING, EMPTY_STRING)).thenReturn(
            Observable.just(
                reviewsListViewState
            )
        )
        val responseObserver = reviewsViewModel.reviewListData().testObserver()
        reviewsViewModel.fetchReviewList(EMPTY_STRING, EMPTY_STRING)
        Truth.assertThat(responseObserver.observedValues).isEqualTo(listOf(reviewsListViewState))
    }

}

