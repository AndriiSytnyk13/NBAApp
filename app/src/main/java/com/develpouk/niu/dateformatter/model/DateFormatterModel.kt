package com.develpouk.niu.dateformatter.model

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateFormatterModel @Inject constructor() {

    fun getFormattedDateMatches(dayDifference: Int): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, dayDifference)

        return dateFormat.format(calendar.time)
    }

    fun getFormattedDays(dayDifference: Int) = getFormattedDateMatches(dayDifference).substring(8, 10)
}