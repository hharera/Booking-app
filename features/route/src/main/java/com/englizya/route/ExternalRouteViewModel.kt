package com.englizya.route

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.LineDetails
import com.englizya.model.model.Routes
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.launch

class ExternalRouteViewModel constructor(
    private val routeRepository: RouteRepository,
    private val userDataStore: UserDataStore,

    ): BaseViewModel() {

    private val _externalLines = MutableLiveData<List<Routes>>()
    val externalLines: MutableLiveData<List<Routes>>
        get() = _externalLines

    fun getExternalRoutes() = viewModelScope.launch {
        updateLoading(true)
        routeRepository
            .getExternalLines()
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