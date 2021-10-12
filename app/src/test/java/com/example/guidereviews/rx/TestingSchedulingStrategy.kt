package com.example.guidereviews.rx

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

open class TestSchedulingStrategyFactory(subscribingScheduler: Scheduler, observingScheduler: Scheduler) :
    SchedulingStrategyFactory(subscribingScheduler, observingScheduler) {
    companion object {
        fun immediate(): TestSchedulingStrategyFactory {
            return TestSchedulingStrategyFactory(Schedulers.trampoline(), Schedulers.trampoline())
        }
    }
}