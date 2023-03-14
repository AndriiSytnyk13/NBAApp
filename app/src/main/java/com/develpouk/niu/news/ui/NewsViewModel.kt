package com.develpouk.niu.news.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.develpouk.niu.dateformatter.repository.DateFormatterRepository
import com.develpouk.niu.news.model.Article
import com.develpouk.niu.news.model.NewsResponse
import com.develpouk.niu.news.repository.NewsRepository
import com.develpouk.niu.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
) :
    ViewModel() {
    val newsResponse: StateFlow<NetworkResponse<List<Article>>> = flow {
        emit(NetworkResponse.Loading)
        val response =
            newsRepository.getNews().articles
        emit(NetworkResponse.Success(response))
    }.catch {
        emit(NetworkResponse.Error("Please try again soon"))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NetworkResponse.Loading)


}