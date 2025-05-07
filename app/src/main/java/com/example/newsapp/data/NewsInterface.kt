package com.example.newsapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("everything?q=tesla&from=2025-04-07&sortBy=publishedAt&language=en")
    suspend fun getNews(
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}