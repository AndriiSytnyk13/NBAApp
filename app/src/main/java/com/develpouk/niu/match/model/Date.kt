package com.develpouk.niu.match.model

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcel.ParcelException
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

@Parcelize
data class Date(
    @SerializedName("start")
    private val start: String,
) : Parcelable {

    fun getFormattedTime(): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        val date = format.parse(start)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(date ?: "-")
    }

    fun getFormattedDate(): String {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        var date: Date? = null
        try {
            date = format.parse(start)
        } catch (_: ParcelException) { }
        return dateFormat.format(date ?: "-")
    }
}