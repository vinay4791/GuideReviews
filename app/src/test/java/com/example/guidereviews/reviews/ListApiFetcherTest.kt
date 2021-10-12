package com.example.guidereviews.reviews

import com.example.guidereviews.reviews.backend.ListApiFetcher
import com.example.guidereviews.reviews.backend.ListBackend
import com.example.guidereviews.reviews.backend.ListResponse
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""
@RunWith(MockitoJUnitRunner::class)
class ListApiFetcherTest {

    @Mock
    private lateinit var listBackend: ListBackend
    @Mock
    private lateinit var listResponse: ListResponse

    private lateinit var listApiFetcher: ListApiFetcher

    @Before
    fun setup() {
        whenever(listBackend.fetchReviewListDetails(EMPTY_STRING, EMPTY_STRING)).thenReturn(Single.just(listResponse))
        listApiFetcher = ListApiFetcher(listBackend)

    }

    @Test
    fun `should load review list data from server`() {
        val testObserver = listApiFetcher.fetchReviewListDetails("","").test()
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
    }


}