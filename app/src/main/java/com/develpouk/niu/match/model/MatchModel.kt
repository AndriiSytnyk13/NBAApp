package com.develpouk.niu.match.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchModel(
    @SerializedName("date")
    private val date: Date,
    @SerializedName("id")
    val id: Int,
    @SerializedName("scores")
    val scores: Scores,
    @SerializedName("teams")
    val teams: Teams,
): Parcelable {
    fun getDate() = date.getFormattedDate()

    fun getTime() = date.getFormattedTime()
}