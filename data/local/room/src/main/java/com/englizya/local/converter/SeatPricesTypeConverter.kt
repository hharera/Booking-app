package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.BookingOfficeList
import com.englizya.model.model.SeatPrices
import com.google.gson.Gson

class SeatPricesTypeConverter {
    @TypeConverter
    fun fromSeatPricesListToGson(seatPricesList: List<SeatPrices>): String {
        return Gson().toJson(seatPricesList)
    }

    @TypeConverter
    fun fromGsonToSeatPricesList(seatPricesList: String): List<SeatPrices> {
        return Gson().fromJson(seatPricesList,Array<SeatPrices>::class.java).toList()
    }
}