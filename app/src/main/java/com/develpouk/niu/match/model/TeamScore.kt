package com.develpouk.niu.match.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class TeamScore(
    @SerializedName("linescore")
    val linescore: List<String>,
    @SerializedName("points")
    val points: Int,
): Parcelable