package com.example.guidereviews.reviews.backend

data class ListResponse(
    val reviews : List<Review>?,
    val pagination: Pagination?,
    val averageRating: Double?,
    val totalCount: Int?
)

data class Review(
    val id: Int?,
    val author: Author?,
    val title: String?,
    val message: String?,
    val isAnonymous: Boolean?,
    val fullName: String?,
    val rating: Int?,
    val created: String?,
    val optionId: Int?,
    val activityId: Int?,
    val language: String?,
    val travelerType: String?
)

data class Author(
    val fullName: String?,
    val country: String?,
    val photo: String?
)

data class Pagination(
    val limit: Int?,
    val offset: Int?
)