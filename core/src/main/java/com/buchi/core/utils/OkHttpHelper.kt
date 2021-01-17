package com.buchi.core.utils

import android.util.Log
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * An helper class that takes a baseUrl while creating an instance. This class has functions that
 * can be used to make api request while building the okHttpClient and appending urls to the base url
 * @param baseUrl for any api requests made on this instance.
 */
class OkHttpHelper(private val baseUrl: String) {
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .callTimeout(60, TimeUnit.SECONDS)
        .build()

    /**
     * Append url to the baseUrl used in creating an instance of this class and return request builder
     * based on the verb of api request to be made.
     * @param url of the api request to make a complete endpoint when appended to baseUrl.
     * @param requestBody to specify POST request with the specified body. If not passed, default is
     * GET request.
     */
    private fun appendUrl(url: String, requestBody: RequestBody? = null): Request.Builder {
        val newUrl = baseUrl + url
        Log.d(javaClass.simpleName, "Request url: $newUrl")
        return if (requestBody != null) {
            Request.Builder()
                .url(newUrl)
                .post(requestBody)
        } else {
            Request.Builder().url(newUrl).get()
        }
    }

    fun makeRequest(url: String, body: RequestBody? = null): Response {
        val request = appendUrl(url, body).build()
        Log.d(javaClass.simpleName, "Request: $request\nBody: ${request.body}")
        return okHttpClient.newCall(request = request).execute()
    }
}
