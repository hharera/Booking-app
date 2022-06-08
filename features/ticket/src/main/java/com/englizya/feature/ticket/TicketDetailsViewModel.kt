package com.englizya.feature.ticket

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.navigation.Arguments
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.datastore.UserDataStore
import com.englizya.model.response.UserTicket
import com.englizya.repository.TicketRepository
import kotlinx.coroutines.launch

class TicketDetailsViewModel constructor(
    private val ticketRepository: TicketRepository,
    private val userDataStore: UserDataStore,
) : BaseViewModel() {


    private val _ticketId = MutableLiveData<String?>()
    val ticketId: MutableLiveData<String?>
        get() = _ticketId

    private val _ticket = MutableLiveData<UserTicket>()
    val ticket: MutableLiveData<UserTicket>
        get() = _ticket


    fun getTicketDetails(ticketId : String?) = viewModelScope.launch {
        updateLoading(true)
        if (ticketId != null) {
            ticketRepository
                .getTicketDetails(userDataStore.getToken(),ticketId)
                .onSuccess {
                    updateLoading(false)
                    _ticket.value = it
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }
    }
}