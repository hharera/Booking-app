package com.englizya.common.utils.time

import java.text.SimpleDateFormat
import java.util.*

object TimeOnly {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val TIME_FORMAT = "HH:mm"

    fun map(dateTime: String?): String {
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT);
        val parsed = simpleDateFormat.parse(dateTime)
        return SimpleDateFormat(TIME_FORMAT).format(parsed);
    }
}