package com.example.guidereviews.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReviewsApplications : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ReviewsApplications)
            modules(reviewsAppModules)
        }
    }
}