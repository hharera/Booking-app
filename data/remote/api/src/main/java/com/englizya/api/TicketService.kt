package com.englizya.api

import com.englizya.api.utils.Routing
import com.englizya.api.utils.TIME_OUT
import com.englizya.model.response.UserTicket
import com.google.common.net.HttpHeaders
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.time.Period

interface TicketService {
    suspend fun getTickets(token: String): List<UserTicket>
    suspend fun getTicketDetails(token: String , ticketId : String): UserTicket

}

class TicketServiceImpl constructor(
    private val client: HttpClient
): TicketService {

    override suspend fun getTickets(token: String): List<UserTicket> =
        client.get(Routing.GET_TICKETS) {
            timeout {
                requestTimeoutMillis = TIME_OUT.MILLIS
            }
            headers{
                append(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
        }

    override suspend fun getTicketDetails(token: String , ticketId : String): UserTicket =
        client.get(Routing.GET_TICKET_DETAILS+ticketId.toInt()) {
            timeout {
                requestTimeoutMillis = TIME_OUT.MILLIS
            }
            headers{
                append(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
        }
}