package com.example.guidereviews.util

import android.content.Context
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

private const val EMPTY_STRING = ""

@RunWith(MockitoJUnitRunner::class)
class UtilsTest {

    private val mockedContext = mock(Context::class.java)

    private lateinit var utils: Utils

    @Before
    fun setUp() {
        utils = Utils(mockedContext)
    }

    @Test
    fun `should convert recieved date from api to correct date format string`() {
        val convertedViewState = utils.getFormattedDate("2020-03-07T01:05:16+01:00")
        val expected = "July 3, 2020"
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    @Test
    fun `should return empty string if  recieved date from api is empty`() {
        val convertedViewState = utils.getFormattedDate("")
        val expected = EMPTY_STRING
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    @Test
    fun `should return empty string if  recieved date from api is null`() {
        val convertedViewState = utils.getFormattedDate(null)
        val expected = EMPTY_STRING
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    @Test
    fun `should return appended author and country if  appendAuthorAndCountry is called`() {
        val convertedViewState = utils.appendAuthorAndCountry("Author", "Country")
        val expected = "Author - Country"
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    @Test
    fun `should return empty string if  appendAuthorAndCountry is called with empty string parameters`() {
        val convertedViewState = utils.appendAuthorAndCountry("", "")
        val expected = ""
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    @Test
    fun `should return only country if  appendAuthorAndCountry is called with empty authorString`() {
        val convertedViewState = utils.appendAuthorAndCountry("", "Country")
        val expected = "Country"
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

    @Test
    fun `should return only author if  appendAuthorAndCountry is called with empty countryString`() {
        val convertedViewState = utils.appendAuthorAndCountry("Author", "")
        val expected = "Author"
        Truth.assertThat(convertedViewState).isEqualTo(expected)
    }

}