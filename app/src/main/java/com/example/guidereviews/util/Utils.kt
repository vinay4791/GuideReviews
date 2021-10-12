package com.example.guidereviews.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.guidereviews.util.AppConstants.INPUT_DATE_FORMAT
import com.example.guidereviews.util.AppConstants.OUTPUT_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class Utils(private val appContext: Context) {

    fun hasInternet(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return false
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    fun getFormattedDate(dateString: String?): String {
        if (dateString.isNullOrEmpty()) {
            return AppConstants.EMPTY_STRING
        }
        return dateString.let {
            try {
                val dateSubString = it.substring(AppConstants.EMPTY_INT, AppConstants.TEN_INT);
                val inputFormat = SimpleDateFormat(INPUT_DATE_FORMAT, Locale.ENGLISH)
                val value = inputFormat.parse(dateSubString)
                val outputFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT, Locale.ENGLISH)
                outputFormat.format(value)
            } catch (e: Exception) {
                AppConstants.EMPTY_STRING
            }
        }
    }

    fun appendAuthorAndCountry(authorString: String, countryString: String): String {
        return when {
            authorString.isEmpty() -> {
                countryString
            }
            countryString.isEmpty() -> {
                authorString
            }
            else -> {
                "$authorString - $countryString"
            }
        }

    }


}

