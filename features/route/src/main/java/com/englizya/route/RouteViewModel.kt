package com.englizya.route

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.LineDetails
import com.englizya.model.model.Routes
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.launch

class RouteViewModel constructor(
    private val routeRepository: RouteRepository,

    ): BaseViewModel() {

    private val _externalLines = MutableLiveData<List<Routes>>()
    val externalLines: MutableLiveData<List<Routes>>
        get() = _externalLines

    private val _internalLines = MutableLiveData<List<Routes>>()
    val internalLines: MutableLiveData<List<Routes>>
        get() = _internalLines

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

    fun getInternalRoutes() = viewModelScope.launch {
        updateLoading(true)
        routeRepository
            .getInternalLines()
            .onSuccess {
                updateLoading(false)
                _internalLines.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

}