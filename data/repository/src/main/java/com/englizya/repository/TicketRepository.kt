package com.englizya.repository

import androidx.room.withTransaction
import com.englizya.api.TicketService
import com.englizya.local.ticket.TicketDao
import com.englizya.local.ticket.TicketDatabase
import com.englizya.model.response.CancelTicketResponse
import com.englizya.model.response.UserTicket
import com.englizya.repository.utils.Resource
import com.englizya.repository.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow

interface TicketRepository {

     fun getUserTickets(
        token: String,
        page: Int,
        pageSize: Int,
        forceOnline :Boolean
    ): Flow<Resource<List<UserTicket>>>

    suspend fun getTicketDetails(token: String, ticketId: String): Result<UserTicket>
    suspend fun cancelTicket(token: String, ticketId: String): Result<CancelTicketResponse>
}

class TicketRepositoryImpl constructor(
    private val ticketService: TicketService,
    private val ticketDao: TicketDao,
    private val ticketDB: TicketDatabase,

    ) : TicketRepository {


    override  fun getUserTickets(
        token: String,
        page: Int,
        pageSize: Int,
        forceOnline :Boolean

    ): Flow<Resource<List<UserTicket>>> = networkBoundResource(
        query = {
            ticketDao.getTickets()

        },
        fetch = {
            ticketService.getTickets(token , page , pageSize)

        },
        saveFetchResult = {userTickets->
            ticketDB.withTransaction {
                ticketDao.deleteTickets()
                ticketDao.insertTickets(userTickets)

            }
        },
        shouldFetch = {
            forceOnline
        }
    )

    override suspend fun getTicketDetails(token: String, ticketId: String) = kotlin.runCatching {
        ticketService.getTicketDetails(token, ticketId)
    }

    override suspend fun cancelTicket(token: String, ticketId: String) = kotlin.runCatching {
        ticketService.cancelTicket(token, ticketId).also {
            ticketDao.removeTicket(ticketId.toInt())
        }
    }
}