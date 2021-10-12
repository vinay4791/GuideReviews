package com.example.guidereviews.common

import com.example.guidereviews.api.networkModule
import com.example.guidereviews.reviews.di.reviewListModule
import com.example.guidereviews.util.utilsModule

val reviewsAppModules = listOf(
    networkModule,
    reviewListModule,
    utilsModule
)