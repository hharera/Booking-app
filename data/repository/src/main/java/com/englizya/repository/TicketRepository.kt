package com.englizya.repository

import com.englizya.api.TicketService
import com.englizya.model.response.UserTicket

interface TicketRepository {

    suspend fun getUserTickets(token: String): Result<List<UserTicket>>
}

class TicketRepositoryImpl constructor(
    private val ticketService: TicketService
) : TicketRepository {

    override suspend fun getUserTickets(token: String) = kotlin.runCatching {
        ticketService.getTickets(token)
    }
}