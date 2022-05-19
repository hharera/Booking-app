package com.englizya.api

import com.englizya.api.utils.Routing
import com.englizya.model.response.UserTicket
import com.google.common.net.HttpHeaders
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.time.Period

interface TicketService {
    suspend fun getTickets(token: String): List<UserTicket>
}

class TicketServiceImpl constructor(
    private val client: HttpClient
): TicketService {

    override suspend fun getTickets(token: String): List<UserTicket> =
        client.get(Routing.GET_TICKETS) {
            timeout {
                requestTimeoutMillis = org.joda.time.Period.seconds(30).millis.toLong()
            }
            headers{
                append(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
        }
}