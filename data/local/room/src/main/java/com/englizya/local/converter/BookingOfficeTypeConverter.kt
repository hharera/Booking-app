package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.BookingOffice
import com.google.gson.Gson

class BookingOfficeTypeConverter {
    @TypeConverter
    fun fromBookingOfficeToGson(bookingOffice: BookingOffice): String {
        return Gson().toJson(bookingOffice)
    }

    @TypeConverter
    fun fromGsonToBookingOffice(bookingOffice: String): BookingOffice {
        return Gson().fromJson(bookingOffice,BookingOffice::class.java)
    }
}