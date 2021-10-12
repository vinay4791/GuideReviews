package com.example.guidereviews.reviews.backend

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ListBackend {

    @GET("/activities/23776/reviews")
    fun fetchReviewListDetails(@Query("offset") offset: String,
                               @Query("limit") limit: String): Single<ListResponse>

}