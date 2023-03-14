package com.develpouk.niu.match.repository

import com.develpouk.niu.match.model.MatchResponse
import com.develpouk.niu.util.MatchService
import retrofit2.http.Query
import javax.inject.Inject

class MatchRepository @Inject constructor(private val matchService: MatchService) {

    suspend fun getMatches(date: String) =  matchService.getMatches(date = date)

    suspend fun getHead2HeadMatches(teamsId: String) = matchService.getHead2HeadMatches(teamsId = teamsId)
}