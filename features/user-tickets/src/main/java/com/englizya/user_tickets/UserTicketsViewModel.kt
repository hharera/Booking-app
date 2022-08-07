package com.englizya.user_tickets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
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

//    private val _tickets = MutableLiveData<List<UserTicket>>()
//    val tickets: LiveData<List<UserTicket>> = _tickets
//

    private val _cancelTicketStatus = MutableLiveData<CancelTicketResponse>()
    val cancelTicketStatus: LiveData<CancelTicketResponse> = _cancelTicketStatus

    private val _page = MutableLiveData(0)
    val page: LiveData<Int> = _page

    private val _pageSize = MutableLiveData(5)
    private val pageSize: LiveData<Int> = _pageSize

    var tickets = ticketRepository.getUserTickets(userDataStore.getToken() , page.value!!,pageSize.value!!,false).asLiveData()



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

    fun getUserTickets(forceOnline: Boolean) {
        ticketRepository.getUserTickets(
            userDataStore.getToken(),
            page.value!!,
            pageSize.value!!,
            forceOnline
        ).asLiveData().let {
//            Log.d("Tickets Flow" , it.toString())
//            Log.d("Tickets" , it.asLiveData().value?.data.toString())
//            Log.d("Tickets Error" , it.asLiveData().value?.error.toString())
            tickets = it
        }

    }
}