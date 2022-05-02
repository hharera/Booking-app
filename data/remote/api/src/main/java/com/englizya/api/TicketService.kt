package com.englizya.api

import com.englizya.api.utils.Routing
import com.englizya.model.response.OnlineTicket
import com.englizya.model.response.UserTicket
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.util.*

interface TicketService {
    suspend fun getTickets(token: String): List<UserTicket>
}

class TicketServiceImpl constructor(
    private val client: HttpClient
): TicketService {

    @OptIn(InternalAPI::class)
    override suspend fun getTickets(token: String): List<UserTicket> =
        client.get(Routing.GET_TICKETS) {
            headers{
                append("Authorization", "Bearer $token")
            }
        }

}