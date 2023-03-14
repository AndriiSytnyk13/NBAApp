package com.develpouk.niu.news.repository

import com.develpouk.niu.util.NewsService
import javax.inject.Inject

class NewsRepository @Inject constructor(private val newsService: NewsService) {

    suspend fun getNews() = newsService.getNews()
}