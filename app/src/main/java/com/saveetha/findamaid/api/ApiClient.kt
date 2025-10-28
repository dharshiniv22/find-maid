package com.saveetha.findamaid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

//    private const val BASE_URL = "https://dudishly-nonsoluble-nohemi.ngrok-free.dev/findamaid/"
//    const val IMAGE_URL = "https://dudishly-nonsoluble-nohemi.ngrok-free.dev/findamaid/uploads/"

    private const val BASE_URL = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/"
    const val IMAGE_URL = "https://12l7sjb3-80.inc1.devtunnels.ms/findamaid/uploads/"


    private val retrofit: Retrofit by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
