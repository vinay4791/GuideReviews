package com.example.guidereviews.reviews.viewstate

import com.example.guidereviews.api.ErrorType

sealed class ReviewListViewState {

    object Loading : ReviewListViewState()

    data class Success(val reviewDetails: ReviewDetails) : ReviewListViewState()

    data class Error(val errorType : ErrorType) : ReviewListViewState()

}