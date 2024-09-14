package com.example.newsapp.API

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager {

    private var retrofit: Retrofit

    private lateinit var logging: HttpLoggingInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private fun getInterceptor(): HttpLoggingInterceptor {
        logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }

    private fun getOkHttpClient(): OkHttpClient {
        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .build()
        return okHttpClient
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient())
            .build()
    }


    fun getNewsSources():NewsService {
        return retrofit.create(NewsService::class.java)
    }
}