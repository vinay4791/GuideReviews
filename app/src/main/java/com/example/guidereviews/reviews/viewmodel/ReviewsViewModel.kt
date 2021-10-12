package com.example.guidereviews.reviews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guidereviews.api.ErrorType
import com.example.guidereviews.base.BaseViewModel
import com.example.guidereviews.reviews.ReviewsListRepository
import com.example.guidereviews.reviews.viewstate.ReviewDetails
import com.example.guidereviews.reviews.viewstate.ReviewItem
import com.example.guidereviews.reviews.viewstate.ReviewListViewState
import com.example.guidereviews.util.AppConstants.INITIAL_OFFSET
import com.example.guidereviews.util.AppConstants.PAGE_LIMIT
import io.reactivex.disposables.CompositeDisposable

class ReviewsViewModel constructor(private val repository: ReviewsListRepository) :
    BaseViewModel() {

    private val disposable = CompositeDisposable()
    private val reviewListData = MutableLiveData<ReviewListViewState>()
    private val selectedReviewItem = MutableLiveData<ReviewItem>()
    var reviewDetailsCache: ReviewDetails? = null
    var paginationOffset = INITIAL_OFFSET
    var totalCount = 0

    /*
     Assigning mutable livedata to respective livedata is done for separation of concerns.
     Views - Fragments and Activities - shouldn’t be able of updating LiveData and thus their own
     state because that’s the responsibility of ViewModels. Views should be able to only observe LiveData.
     Hence  encapsulating access to MutableLiveData.
     */
    fun reviewListData(): LiveData<ReviewListViewState> = reviewListData
    fun getSelectedReviewItem(): LiveData<ReviewItem> = selectedReviewItem

    fun setSelectedReviewItem(reviewItem: ReviewItem) {
        selectedReviewItem.value = reviewItem
    }

    fun fetchReviewList(offset: String = INITIAL_OFFSET, limit : String = PAGE_LIMIT) {
        disposable.add(
            repository.fetchReviewList(offset, limit)
                .subscribe({ response ->
                    val reviewListViewState = when (response) {
                        is ReviewListViewState.Success -> {
                            paginationOffset = (response.reviewDetails.pagination.offset +  response.reviewDetails.pagination.limit).toString()
                            totalCount = response.reviewDetails.totalCount
                            if(reviewDetailsCache == null || offset == INITIAL_OFFSET){
                                    reviewDetailsCache = ReviewDetails.EMPTY
                                    reviewDetailsCache = response.reviewDetails
                                    response
                            } else{
                                val oldReviews = reviewDetailsCache?.reviewItemList ?: emptyList()
                                reviewDetailsCache = ReviewDetails(
                                    oldReviews.plus( response.reviewDetails.reviewItemList),
                                    response.reviewDetails.totalCount,
                                    response.reviewDetails.averageRating,
                                    response.reviewDetails.pagination
                                )
                                ReviewListViewState.Success(
                                    reviewDetailsCache!!
                                )
                            }
                        }
                        else -> response
                    }
                    reviewListData.postValue(reviewListViewState)
                },
                    { error ->
                        reviewListData.postValue(ReviewListViewState.Error(ErrorType.UNKNOWN))
                    })
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}