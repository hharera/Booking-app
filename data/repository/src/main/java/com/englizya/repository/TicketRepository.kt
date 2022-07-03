package com.englizya.repository

import com.englizya.api.TicketService
import com.englizya.model.response.CancelTicketResponse
import com.englizya.model.response.UserTicket

interface TicketRepository {

    suspend fun getUserTickets(token: String, page: Int, pageSize: Int): Result<List<UserTicket>>
    suspend fun getTicketDetails(token: String, ticketId: String): Result<UserTicket>
    suspend fun cancelTicket(token: String , ticketId : String): Result<CancelTicketResponse>
}

class TicketRepositoryImpl constructor(
    private val ticketService: TicketService
) : TicketRepository {

    override suspend fun getUserTickets(token: String, page: Int, pageSize: Int) =
        kotlin.runCatching {
            ticketService.getTickets(token, page, pageSize)
        }

    override suspend fun getTicketDetails(token: String, ticketId: String) = kotlin.runCatching {
        ticketService.getTicketDetails(token, ticketId)
    }

    override suspend fun cancelTicket(token: String, ticketId: String) = kotlin.runCatching {
        ticketService.cancelTicket(token, ticketId)
    }
}