package com.example.guidereviews.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This  class exposes two static functions for deciding which scheduler the observable should subscribeOn
 */
class AndroidSchedulingStrategyFactory(subscribingScheduler: Scheduler) :
        SchedulingStrategyFactory(subscribingScheduler, AndroidSchedulers.mainThread()) {

    companion object {
        fun newThread(): AndroidSchedulingStrategyFactory {
            return AndroidSchedulingStrategyFactory(Schedulers.newThread())
        }

        fun io(): AndroidSchedulingStrategyFactory {
            return AndroidSchedulingStrategyFactory(Schedulers.io())
        }
    }

}
