package com.example.guidereviews.rx

import io.reactivex.functions.Function

/**
 * This  interface is an extension of reactivex.functions.Function which acts as the building block of converters used in the project.
 * An api response goes into the converter and a viewstate comes out of the converter.
 */
interface Converter<T, R> : Function<T, R>