package com.englizya.common.utils.time

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatterBuilder
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object TimeOnly {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_TIME_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    private const val TIME_FORMAT_12 = "hh:mm aa"
    private const val TIME_FORMAT_24 = "HH:mm"

    fun map(dateTime: String?): String? {
        if (dateTime == null) {
            return ""
        }
        return DateTime.parse(dateTime).toLocalDateTime().toString(TIME_FORMAT_12)
    }

    fun timeIn24TimeSystem(dateTime: String?): String? {
        if (dateTime == null) {
            return ""
        }
        return DateTime.parse(dateTime).toLocalDateTime().toString(TIME_FORMAT_24)
    }


    fun ToTime(dateTime: String?): String? {
        dateTime ?: return null
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT_1);
        val parsed = simpleDateFormat.parse(dateTime)
        return SimpleDateFormat(TIME_FORMAT_12).format(parsed);
    }
}