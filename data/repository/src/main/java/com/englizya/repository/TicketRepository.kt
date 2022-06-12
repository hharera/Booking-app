package com.englizya.repository

import com.englizya.api.TicketService
import com.englizya.model.response.UserTicket

interface TicketRepository {

    suspend fun getUserTickets(token: String): Result<List<UserTicket>>
    suspend fun getTicketDetails(token: String , ticketId : String): Result<UserTicket>
    suspend fun cancelTicket(token: String , ticketId : String): Result<Boolean>


}

class TicketRepositoryImpl constructor(
    private val ticketService: TicketService
) : TicketRepository {

    override suspend fun getUserTickets(token: String) = kotlin.runCatching {
        ticketService.getTickets(token)
    }


    override suspend fun getTicketDetails(token: String , ticketId : String) = kotlin.runCatching {
        ticketService.getTicketDetails(token , ticketId)
    }

    override suspend fun cancelTicket(token: String, ticketId: String)= kotlin.runCatching {
        ticketService.cancelTicket(token , ticketId)
    }
}