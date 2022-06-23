package com.englizya.route

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.LineDetails
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.launch

class ExternalRouteViewModel constructor(
    private val routeRepository: RouteRepository,
    private val userDataStore: UserDataStore,

    ): BaseViewModel() {

    private val _externalLines = MutableLiveData<List<LineDetails>>()
    val externalLines: MutableLiveData<List<LineDetails>>
        get() = _externalLines

    fun getExternalRoutes() = viewModelScope.launch {
        updateLoading(true)
        routeRepository
            .getExternalLines(userDataStore.getToken())
            .onSuccess {
                updateLoading(false)
                _externalLines.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

}