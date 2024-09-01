package com.example.newsapp.API

import com.example.newsapp.ModelNewsSource.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("v2/top-headlines/sources")

    fun getNewsSources(@Query("apiKey") apiKey:String = "ca29e585238b4f6d8612912a8a607083"): Call<SourceResponse>

    @GET("v2/everything")
    fun getNewsContent(@Query("sources") sourceId:String,
                       @Query("apikey") apiKey: String = "ca29e585238b4f6d8612912a8a607083")
    :Call<com.example.newsapp.ModelNewsContent.SourceResponse>
}