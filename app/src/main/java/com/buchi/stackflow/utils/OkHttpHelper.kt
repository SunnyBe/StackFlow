package com.buchi.stackflow.utils

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class OkHttpHelper(private val baseUrl: String) {
    private val okHttpClient = OkHttpClient()

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
        Log.d(javaClass.simpleName, "Request: $request")
        return okHttpClient.newCall(request = request).execute()
    }
}
