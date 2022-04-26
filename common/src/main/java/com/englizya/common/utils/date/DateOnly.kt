package com.englizya.common.utils.date

import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

object DateOnly {

    private const val DATE_FORMAT = "dd-MM-yyyy"
    private const val MONTH_DATE = "dd MMM yyyy"
    private const val SPACE_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss"

    fun map(dateTime: DateTime): Date {
        val also = SimpleDateFormat(DATE_FORMAT);
        val format = also.format(dateTime.toDate())
        val parse = also.parse(format)
        return parse
    }

    fun map(dateTime: String): String {
        var simpleDateFormat = SimpleDateFormat(SPACE_DATE_FORMAT);
        val format = simpleDateFormat.parse(dateTime)

        simpleDateFormat = SimpleDateFormat(MONTH_DATE);
        return simpleDateFormat.format(format)
    }
}