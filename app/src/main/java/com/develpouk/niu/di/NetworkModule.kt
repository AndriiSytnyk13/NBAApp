package com.develpouk.niu.di

import com.develpouk.niu.util.MatchService
import com.develpouk.niu.util.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val MATCHES_BASE = "https://v2.nba.api-sports.io/"
    private const val HEADER_NAME = "x-rapidapi-key"
    private const val HEADER_KEY = "caa63d13fd15b7d5bdc498710da136a3"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request()
        val newRequest = request.newBuilder().addHeader(HEADER_NAME, HEADER_KEY).build()
        chain.proceed(newRequest)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(MATCHES_BASE).addConverterFactory(GsonConverterFactory.create())
            .client(client).build()


    @Provides
    @Singleton
    fun provideMatchService(retrofit: Retrofit): MatchService = retrofit.create(MatchService::class.java)

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService = retrofit.create(NewsService::class.java)


}