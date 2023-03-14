package com.develpouk.niu.match.model

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("response")
    val response: List<MatchModel>,
)