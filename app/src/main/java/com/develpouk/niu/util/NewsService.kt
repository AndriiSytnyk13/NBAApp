package com.develpouk.niu.util

import com.develpouk.niu.news.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Url


private const val NEWS_URL =
    "https://newsapi.org/v2/everything?q=NBA&language=en&sortBy=publishedAt&apiKey=bf4e7635c78649a1b14adf12c0911788"
interface NewsService {

    @GET
    suspend fun getNews(@Url url: String = NEWS_URL): NewsResponse
}