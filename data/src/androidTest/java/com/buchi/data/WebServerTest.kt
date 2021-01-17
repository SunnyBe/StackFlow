package com.buchi.data

import com.google.gson.Gson
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
open class WebServerTest {

    val server: MockWebServer = MockWebServer()
    val MOCK_WEBSERVER_PORT = 8081

    lateinit var retrofit: Retrofit

    @Before
    open fun init() {
        server.start(MOCK_WEBSERVER_PORT)
        retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    @After
    fun cleanUp() {
        server.shutdown()
    }


}