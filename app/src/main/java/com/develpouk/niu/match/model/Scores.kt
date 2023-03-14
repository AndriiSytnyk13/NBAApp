package com.develpouk.niu.match.model

import android.os.Parcelable
import com.develpouk.niu.enums.MatchStatus
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class Scores(
    @SerializedName("home")
    val home: TeamScore,
    @SerializedName("visitors")
    val visitors: TeamScore,
): Parcelable {
    fun matchStatus(): MatchStatus =
        if (home.points > visitors.points) {
            MatchStatus.HOME_WIN
        } else if (home.points < visitors.points) {
            MatchStatus.VISITOR_WIN
        } else {
            MatchStatus.MATCH_NOT_STARTED
        }
}