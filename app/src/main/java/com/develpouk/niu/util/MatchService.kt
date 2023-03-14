package com.develpouk.niu.util

import com.develpouk.niu.match.model.MatchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchService {


    @GET("games")
    suspend fun getMatches(@Query("date") date: String): MatchResponse

    @GET("games")
    suspend fun getHead2HeadMatches(@Query("h2h") teamsId: String): MatchResponse
}