package com.saveetha.findmaid.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {


    private const val BASE_URL = "https://4n6j9p4w-80.inc1.devtunnels.ms/findamaid/"
    const val IMAGE_URL = "https://4n6j9p4w-80.inc1.devtunnels.ms/findamaid/uploads/"

    private val retrofit: Retrofit by lazy {

        // Logging interceptor for network debugging
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // OkHttpClient setup with timeouts
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        // Retrofit instance
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Singleton API service instance
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
