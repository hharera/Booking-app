package com.englizya.common.mapper

import org.joda.time.DateTime

object DateTimeMapper {

    fun map(seconds: Long) = DateTime(seconds)
}