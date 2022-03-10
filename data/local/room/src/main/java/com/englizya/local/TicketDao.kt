package com.englizya.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TicketDao {

    @Insert
    fun insertTicket(ticket: Ticket)

    @Insert
    fun insertTickets(tickets: List<Ticket>)

    @Query(value = "SELECT * from Ticket")
    fun getTickets(): List<Ticket>

    @Query(value = "DELETE from Ticket")
    fun deleteAll()

    @Query(value = "DELETE from UnPrintedTicket")
    fun deleteAllUnPrintedTickets()

    @Insert
    fun insertUnPrintedTicket(UnPrintedTicket : UnPrintedTicket)

    @Query(value = "SELECT * from UnPrintedTicket")
    fun getAllSavedTickets(): List<UnPrintedTicket>
}