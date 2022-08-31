package com.englizya.user_tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.response.CancelTicketResponse
import com.englizya.model.response.UserTicket
import com.englizya.repository.TicketRepository
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.launch

class UserTicketsViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _cancelTicketStatus = MutableLiveData<CancelTicketResponse>()
    val cancelTicketStatus: LiveData<CancelTicketResponse> = _cancelTicketStatus

    private val _page = MutableLiveData(0)
    val page: LiveData<Int> = _page

    private val _pageSize = MutableLiveData(5)
    private val pageSize: LiveData<Int> = _pageSize

    private val _tickets: MutableLiveData<Resource<List<UserTicket>>> = MutableLiveData()
    val tickets: LiveData<Resource<List<UserTicket>>> = getUserTickets(true)

    fun cancelTicket(ticketId: String) = viewModelScope.launch {
        updateLoading(true)
        ticketRepository
            .cancelTicket(userDataStore.getToken(), ticketId)
            .onSuccess {
                updateLoading(false)
                _cancelTicketStatus.value = it

            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }

    }

    fun nextTicketsPage(): LiveData<Resource<List<UserTicket>>> {
        _page.value = _page.value?.plus(1)
        return getUserTickets(true)
    }

    fun previousTicketsPage(): LiveData<Resource<List<UserTicket>>> {
        val currentPage = page.value!!
        _page.value = maxOf(currentPage - 1, 0)
        return getUserTickets(true)
    }

    fun getFirstPageUserTickets() {
        _page.value = 0
        getUserTickets(true)
    }

    fun getUserTickets(forceOnline: Boolean): LiveData<Resource<List<UserTicket>>> {
        return ticketRepository.getUserTickets(
            userDataStore.getToken(),
            page.value!!,
            pageSize.value!!,
            forceOnline
        ).asLiveData().also {
            _tickets.value = it.value
        }
    }
}