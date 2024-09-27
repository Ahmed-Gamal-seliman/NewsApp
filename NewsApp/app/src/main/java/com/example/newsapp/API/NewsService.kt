package com.example.newsapp.API

import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("category") category:String,
        @Query("apiKey") apiKey:String="ca29e585238b4f6d8612912a8a607083"): Call<SourceResponse>

    @GET("v2/everything")
    fun getNewsBySource(@Query("apiKey") apiKey: String= "ca29e585238b4f6d8612912a8a607083",
                        @Query("sources") sourceId:String,
                        @Query("language") language:String="en"): Call<NewsResponse>


   
}