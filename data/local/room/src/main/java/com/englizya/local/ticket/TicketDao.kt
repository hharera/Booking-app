package com.englizya.local.ticket

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.response.UserTicket
import kotlinx.coroutines.flow.Flow

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTickets(ticket:List<UserTicket>)



    @Query(value = "SELECT * from Ticket ")
    fun getTickets(): Flow<List<UserTicket>>

    @Query(value = "DELETE FROM Ticket WHERE ticketId = :ticketId")
    fun removeTicket(ticketId:Int)

    @Query(value = "DELETE from Ticket")
    fun deleteTickets()
}