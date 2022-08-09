package com.englizya.user_tickets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.response.CancelTicketResponse
import com.englizya.model.response.UserTicket
import com.englizya.repository.TicketRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

class UserTicketsViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _tickets = MutableLiveData<List<UserTicket>>()
    val tickets: MutableLiveData<List<UserTicket>>
        get() = _tickets

    private val _cancelTicketStatus = MutableLiveData<CancelTicketResponse>()
    val cancelTicketStatus: MutableLiveData<CancelTicketResponse>
        get() = _cancelTicketStatus

    private val _page = MutableLiveData(0)
    val page: LiveData<Int> = _page

    private val pageSize = 5

    init {
        getUserTickets(false)
    }

    fun getUserTickets(forceOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        ticketRepository
            .getUserTickets(userDataStore.getToken(), page.value!!, pageSize, forceOnline)
            .onSuccess {
                updateLoading(false)
                _tickets.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

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

    fun nextTicketsPage() {
        _page.value = _page.value?.plus(1)
        getUserTickets(false)
    }

    fun getFirstPageUserTickets() {
        _page.value = 0
        getUserTickets(false)
    }

    fun getPreviousTicketsPage() {
        if (_page.value!! <= 0) {
            if (tickets.value?.isNotEmpty() == true) {
                return
            } else {
                getUserTickets(true)
            }
        } else {
            _page.value = max(page.value!! - 1, 0)
            getUserTickets(true)
        }
    }

    fun getNextTicketsPage() {
        _page.value = page.value!! + 1
        getUserTickets(true)
    }

    fun resetPages() {
        _page.value = 0
        getUserTickets(true)
    }
}