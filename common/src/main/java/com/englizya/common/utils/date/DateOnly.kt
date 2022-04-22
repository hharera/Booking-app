package com.englizya.common.utils.date

import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

object DateOnly {

    private const val DATE_FORMAT = "dd-MM-yyyy"

    fun map(dateTime: DateTime): Date {
        val also = SimpleDateFormat(DATE_FORMAT);
        val format = also.format(dateTime.toDate())
        val parse = also.parse(format)
        return parse
    }
}