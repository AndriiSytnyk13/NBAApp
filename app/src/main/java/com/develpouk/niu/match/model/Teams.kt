package com.develpouk.niu.match.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Teams(
    @SerializedName("home")
    val home: Team,
    @SerializedName("visitors")
    val visitors: Team
): Parcelable