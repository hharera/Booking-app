package com.englizya.common.utils.time

import java.text.SimpleDateFormat

object TimeOnly {

    private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_TIME_FORMAT_1 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"

    private const val TIME_FORMAT_12 = "hh:mm aa"
    private const val TIME_FORMAT_24 = "HH:mm"

    fun map(dateTime: String?): String? {
        dateTime ?: return null
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT);
        val parsed = simpleDateFormat.parse(dateTime)
        return SimpleDateFormat(TIME_FORMAT_12).format(parsed);
    }

    fun timeIn24TimeSystem(dateTime: String?): String? {
        dateTime ?: return null
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT);
        val parsed = simpleDateFormat.parse(dateTime)
        return SimpleDateFormat(TIME_FORMAT_24).format(parsed);
    }


    fun ToTime(dateTime: String?): String? {
        dateTime ?: return null
        val simpleDateFormat = SimpleDateFormat(DATE_TIME_FORMAT_1);
        val parsed = simpleDateFormat.parse(dateTime)
        return SimpleDateFormat(TIME_FORMAT_12).format(parsed);
    }
}