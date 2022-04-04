package com.englizya.common.mapper

import org.joda.time.DateTime
import java.text.SimpleDateFormat

object DateStringMapper {

    private const val DATE_FORMAT = "dd-MM-yyyy"

    fun map(dateTime: DateTime) = SimpleDateFormat(DATE_FORMAT).format(dateTime.toDate())
}