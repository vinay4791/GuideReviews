package com.example.guidereviews.rx

import io.reactivex.*

/**
 * This  class exposes two static functions  which basically returns a Observable or Single rxObservable with the
   attached subscribing or observing scheduler
 */
class SchedulingStrategy<T> constructor(private val subscribingScheduler: Scheduler,
                                        private val observingScheduler: Scheduler) :
        ObservableTransformer<T, T>, SingleTransformer<T, T> {

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .subscribeOn(subscribingScheduler)
                .observeOn(observingScheduler)
    }

    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream
                .subscribeOn(subscribingScheduler)
                .observeOn(observingScheduler)
    }
}
