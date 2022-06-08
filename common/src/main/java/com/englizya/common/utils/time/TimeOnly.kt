package com.englizya.common.utils.time

import java.text.SimpleDateFormat

object TimeOnly {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val TIME_FORMAT = "hh:mm aa"

    fun map(dateTime: String?): String? {
        dateTime ?: return null
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT);
        val parsed = simpleDateFormat.parse(dateTime)
        return SimpleDateFormat(TIME_FORMAT).format(parsed);
    }
}