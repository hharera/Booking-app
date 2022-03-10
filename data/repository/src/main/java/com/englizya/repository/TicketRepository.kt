package com.englizya.repository


interface TicketRepository {

    suspend fun insertTicket(ticket : Ticket, forceOnline : Boolean) : Result<Unit>
    suspend fun insertUnPrintedTicket(ticket : Ticket) : Result<Unit>
    suspend fun insertTickets(tickets : List<Ticket>, forceOnline : Boolean) : Result<Unit>
    suspend fun getLocalTickets(): Result<List<Ticket>>
    suspend fun deleteLocalTickets(): Result<Unit>
    fun getAllUnPrintedTickets(): Result<List<UnPrintedTicket>>
}