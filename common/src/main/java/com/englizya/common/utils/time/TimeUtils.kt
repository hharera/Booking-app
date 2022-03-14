package com.englizya.common.utils.time

import org.joda.time.DateTime
import java.text.SimpleDateFormat

object TimeUtils {

    private val TIME_START = DateTime(2022, 1, 1, 0, 0)
    private const val START_SHIFT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val END_SHIFT_DATE_FORMAT = "dd.MM.yyyy HH:mm:ss"
    private const val MILLIS_IN_SECOND = 1000
    private const val MILLIS_IN_MINUTE = 60000
    private const val MILLIS_IN_HOUR = 3600000
    private const val MILLIS_IN_DAY = 86400000
    private const val MILLIS_IN_WEEK = 604800000
    private const val MILLIS_IN_MONTH = 2592000000
    private const val MILLIS_IN_YEAR = 31536000000

    fun getTicketTimeMillis(): Long = DateTime.now().minus(TIME_START.millis).millis

    fun getDate(date: String): String {
        val dateTime = DateTime.parse(date)
        return "${dateTime.year}-${dateTime.monthOfYear}-${dateTime.dayOfMonth}"
    }

    fun getTime(date: String): String {
        val dateTime = DateTime.parse(date)
        return "${dateTime.hourOfDay}:${dateTime.secondOfMinute}:${dateTime.minuteOfHour}"
    }

    private fun calculateSeconds(millis: Long): Long {
        return millis / MILLIS_IN_SECOND
    }

    private fun calculateMinutes(millis: Long): Long {
        return millis / MILLIS_IN_MINUTE
    }

    private fun calculateHours(millis: Long): Long {
        return millis / MILLIS_IN_HOUR
    }

    private fun calculateDays(millis: Long): Long {
        return millis / MILLIS_IN_DAY
    }

    private fun calculateWeeks(millis: Long): Long {
        return millis / MILLIS_IN_WEEK
    }

    private fun calculateMonths(millis: Long): Long {
        return millis / MILLIS_IN_MONTH
    }

    private fun calculateYears(millis: Long): Long {
        return millis / MILLIS_IN_YEAR
    }
}