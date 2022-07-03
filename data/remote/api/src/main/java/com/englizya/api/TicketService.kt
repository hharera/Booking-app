package com.englizya.api

import com.englizya.api.utils.RequestParam
import com.englizya.api.utils.Routing
import com.englizya.api.utils.TIME_OUT
import com.englizya.model.response.CancelTicketResponse
import com.englizya.model.response.UserTicket
import com.google.common.net.HttpHeaders
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*

interface TicketService {

    suspend fun getTickets(token: String, page: Int, pageSize: Int): List<UserTicket>
    suspend fun getTicketDetails(token: String , ticketId : String): UserTicket
    suspend fun cancelTicket(token: String , ticketId : String): CancelTicketResponse
}

class TicketServiceImpl constructor(
    private val client: HttpClient
): TicketService {

    override suspend fun getTickets(token: String, page: Int, pageSize: Int): List<UserTicket> =
        client.get(Routing.GET_TICKETS) {
            timeout {
                requestTimeoutMillis = TIME_OUT.MILLIS
            }
            parameter(RequestParam.PAGE_SIZE, "$pageSize")
            parameter(RequestParam.PAGE, "$page")
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

    override suspend fun cancelTicket(token: String, ticketId: String): CancelTicketResponse =
        client.get("${Routing.CANCEL_TICKET}/${ticketId.toInt()}/cancel") {
            timeout {
                requestTimeoutMillis = TIME_OUT.MILLIS
            }
            headers{
                append(HttpHeaders.AUTHORIZATION, "Bearer $token")
            }
        }

}