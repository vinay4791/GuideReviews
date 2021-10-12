package com.example.guidereviews.reviews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.guidereviews.reviews.ReviewsListRepository

class ReviewsViewModelFactory(
    private val repository: ReviewsListRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ReviewsViewModel(repository) as T
}