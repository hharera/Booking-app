package com.englizya.local.converter

import androidx.room.TypeConverter
import com.englizya.model.model.Reservations
import com.englizya.model.model.SeatPrices
import com.google.gson.Gson

class ReservationsTypeConverter {
    @TypeConverter
    fun fromReservationsListToGson(reservationList: ArrayList<Reservations>): String {
        return Gson().toJson(reservationList)
    }

    @TypeConverter
    fun fromGsonToReservationList(reservationList: String): ArrayList<Reservations> {
        return ArrayList(Gson().fromJson(reservationList,Array<Reservations>::class.java).toMutableList())
    }
}