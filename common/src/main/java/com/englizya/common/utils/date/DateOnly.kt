package com.englizya.common.utils.date

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.text.SimpleDateFormat
import java.util.*

object DateOnly {

    private const val DATE_FORMAT = "dd-MM-yyyy"
    private const val DATE_FORMAT_2 = "yyyy-MM-dd"
    private const val MONTH_DATE = "dd MMM yyyy"
    private const val SPACE_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"

    fun toMonthDate(dateTime: DateTime): Date {
        val also = SimpleDateFormat(DATE_FORMAT);
        val format = also.format(dateTime.toDate())
        val parse = also.parse(format)
        return parse
    }

    fun toMonthDate(dateTime: String): String {
        var simpleDateFormat = SimpleDateFormat(DATE_FORMAT_2);
        val format = simpleDateFormat.parse(dateTime.split(" ")[0])

        simpleDateFormat = SimpleDateFormat(MONTH_DATE)
        return simpleDateFormat.format(format)
    }

    fun getTripDate(dateTime: String): String {
        return DateTime.parse(dateTime).let {
            DateTime(
                it.toInstant(), DateTimeZone.forTimeZone(
                    TimeZone.getTimeZone("EET")
                )
            )
        }.toString ("dd MMM yyyy")
    }
}