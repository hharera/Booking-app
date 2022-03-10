package com.englizya.api

interface RemoteTicketService {
    suspend fun insertTicket(ticket : Ticket): Ticket
    suspend fun insertTickets(tickets : List<Ticket>): List<Ticket>
}