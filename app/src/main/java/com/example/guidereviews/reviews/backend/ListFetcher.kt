package com.example.guidereviews.reviews.backend

import io.reactivex.Single

interface  ListFetcher {

    fun fetchReviewListDetails(offset: String, limit: String) : Single<ListResponse>

}