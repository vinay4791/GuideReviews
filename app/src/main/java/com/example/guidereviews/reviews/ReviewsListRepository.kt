package com.example.guidereviews.reviews

import com.example.guidereviews.api.ErrorType
import com.example.guidereviews.reviews.backend.ListApiFetcher
import com.example.guidereviews.reviews.viewstate.ReviewListConverter
import com.example.guidereviews.reviews.viewstate.ReviewListViewState
import com.example.guidereviews.rx.SchedulingStrategyFactory
import com.squareup.moshi.JsonEncodingException
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import retrofit2.HttpException
import java.net.UnknownHostException

class ReviewsListRepository constructor(
    private val listApiFetcher: ListApiFetcher,
    private val reviewsListConverter: ReviewListConverter,
    private val schedulingStrategyFactory: SchedulingStrategyFactory
) {

    fun fetchReviewList(offset: String, limit: String): Observable<ReviewListViewState> {
        val reviewListApiFetcherObservable =
            listApiFetcher.fetchReviewListDetails(offset, limit).toObservable()

        return reviewListApiFetcherObservable
            .map(reviewsListConverter)
            .startWith(ReviewListViewState.Loading)
            .compose(ErrorConverter())
            .compose(schedulingStrategyFactory.create())
    }

    class ErrorConverter : ObservableTransformer<ReviewListViewState, ReviewListViewState> {
        override fun apply(upstream: Observable<ReviewListViewState>): ObservableSource<ReviewListViewState> {
            return upstream.onErrorResumeNext(Function<Throwable, ObservableSource<ReviewListViewState>> { cause ->
                Observable.just(convertToCause(cause))
            })
        }

        private fun convertToCause(cause: Throwable): ReviewListViewState {
            return when (cause) {
                is JsonEncodingException -> ReviewListViewState.Error(ErrorType.UNKNOWN)
                is UnknownHostException -> ReviewListViewState.Error(ErrorType.NO_INTERNET_CONNECTION)
                is HttpException -> {
                    ReviewListViewState.Error(ErrorType.SERVER)
                }
                else -> ReviewListViewState.Error(ErrorType.UNKNOWN)
            }
        }

    }

}