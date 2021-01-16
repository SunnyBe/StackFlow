package com.buchi.stackflow.utils

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://stackoverflw.herokuapp.com/v1/")
            .client(okhttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun okhttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .build()
    }
}
