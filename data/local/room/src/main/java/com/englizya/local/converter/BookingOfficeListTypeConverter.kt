package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.BookingOfficeList
import com.englizya.model.model.LineStation
import com.google.gson.Gson

class BookingOfficeListTypeConverter {
    @TypeConverter
    fun fromBookingOfficeListToGson(bookingOfficeList: List<BookingOfficeList>): String {
        return Gson().toJson(bookingOfficeList)
    }

    @TypeConverter
    fun fromGsonToBookingOfficeList(bookingOfficeList: String): List<BookingOfficeList> {
        return Gson().fromJson(bookingOfficeList,Array<BookingOfficeList>::class.java).toList()
    }
}