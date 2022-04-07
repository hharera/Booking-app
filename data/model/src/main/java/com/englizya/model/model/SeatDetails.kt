package com.englizya.model.model

import com.englizya.model.item.ReservationSeat
import kotlinx.serialization.Serializable

@Serializable
open class SeatDetails {
    var busId: Int? = null
    var rowNo: Int? = null
    var colNo: Int? = null
    var seatNo: Int? = null
    var reservations: List<ReservationSeat> = emptyList()
}