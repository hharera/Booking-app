package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.Reservations
import com.englizya.model.model.Seat
import com.google.gson.Gson

class SeatTypeConverter {
    @TypeConverter
    fun fromSeatsToGson(seats: List<Seat>): String {
        return Gson().toJson(seats)
    }

    @TypeConverter
    fun fromGsonToSeats(seats: String): List<Seat> {
        return Gson().fromJson(seats,Array<Seat>::class.java).toList()
    }
}