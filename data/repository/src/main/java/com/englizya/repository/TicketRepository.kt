package com.englizya.repository

import com.englizya.api.TicketService
import com.englizya.model.response.UserTicket

interface TicketRepository {

    suspend fun getUserTickets(token: String): Result<List<UserTicket>>
    suspend fun getTicketDetails(token: String , ticketId : String): Result<UserTicket>

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
}