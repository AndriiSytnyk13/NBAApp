package com.develpouk.niu.dateformatter.repository

import com.develpouk.niu.dateformatter.model.DateFormatterModel
import javax.inject.Inject

class DateFormatterRepository @Inject constructor(private val dateFormatterModel: DateFormatterModel) {

    fun getFormattedDateMatches(dayDifference: Int) = dateFormatterModel.getFormattedDateMatches(dayDifference)

    fun getFormattedDays(dayDifference: Int) = dateFormatterModel.getFormattedDays(dayDifference)
}