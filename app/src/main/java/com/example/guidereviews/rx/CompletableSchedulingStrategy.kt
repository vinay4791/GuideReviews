package com.example.guidereviews.rx

import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.CompletableTransformer
import io.reactivex.Scheduler

/**
 * This  class exposes two static functions  which returns a Completable with the attached subscribing or observing scheduler
 */
class CompletableSchedulingStrategy constructor(private val subscribingScheduler: Scheduler,
                                                private val observingScheduler: Scheduler) : CompletableTransformer {
    override fun apply(upstream: Completable): CompletableSource {
        return upstream
                .subscribeOn(subscribingScheduler)
                .observeOn(observingScheduler)
    }
}
