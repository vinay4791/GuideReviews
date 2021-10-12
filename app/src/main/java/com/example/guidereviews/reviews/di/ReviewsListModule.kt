package com.example.guidereviews.reviews.di

import com.example.guidereviews.reviews.ReviewsListRepository
import com.example.guidereviews.reviews.adapter.ReviewsListAdapter
import com.example.guidereviews.reviews.backend.ListApiFetcher
import com.example.guidereviews.reviews.backend.ListBackend
import com.example.guidereviews.reviews.viewstate.ReviewListConverter
import com.example.guidereviews.rx.AndroidSchedulingStrategyFactory
import com.example.guidereviews.util.AppConstants
import org.koin.dsl.module
import retrofit2.Retrofit

val reviewListModule = module {

    factory {
        ReviewsListAdapter()
    }
    factory {
        listBackend(get())
    }

    factory {
        ListApiFetcher(get())
    }

    factory {
        ReviewListConverter(get())
    }

    factory {
        ReviewsListRepository(
            get(),
            get(), AndroidSchedulingStrategyFactory.io()
        )
    }

}

fun listBackend(retrofit: Retrofit.Builder): ListBackend {
    return retrofit.baseUrl(AppConstants.BASE_URL).build().create(ListBackend::class.java)
}