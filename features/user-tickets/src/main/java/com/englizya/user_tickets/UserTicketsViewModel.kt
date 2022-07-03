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

    private val _pageSize = MutableLiveData(5)
    private val pageSize: LiveData<Int> = _pageSize

    init {
        getUserTickets()
    }

    fun getUserTickets() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        ticketRepository
            .getUserTickets(userDataStore.getToken(), page.value!!, pageSize.value!!)
            .onSuccess {
                updateLoading(false)
                _tickets.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun cancelTicket(ticketId:String)= viewModelScope.launch {
        updateLoading(true)
        ticketRepository
            .cancelTicket(userDataStore.getToken() , ticketId)
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
        getUserTickets()
    }
}