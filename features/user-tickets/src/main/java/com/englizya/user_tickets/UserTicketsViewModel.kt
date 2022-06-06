package com.englizya.user_tickets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.response.UserTicket
import com.englizya.repository.TicketRepository
import kotlinx.coroutines.launch

class UserTicketsViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {

    private val _tickets = MutableLiveData<List<UserTicket>>()
    val tickets: MutableLiveData<List<UserTicket>>
        get() = _tickets


    init {
        getUserTickets()
    }

    fun getUserTickets() = viewModelScope.launch {
        updateLoading(true)
        ticketRepository
            .getUserTickets(userDataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _tickets.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }


}