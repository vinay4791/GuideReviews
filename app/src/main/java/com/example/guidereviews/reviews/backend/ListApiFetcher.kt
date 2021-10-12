package com.example.guidereviews.reviews.backend

import io.reactivex.Single

class ListApiFetcher constructor(private val listBackend: ListBackend) : ListFetcher {

    override fun fetchReviewListDetails(offset: String, limit: String): Single<ListResponse> {
        return listBackend.fetchReviewListDetails(offset, limit)
    }

}