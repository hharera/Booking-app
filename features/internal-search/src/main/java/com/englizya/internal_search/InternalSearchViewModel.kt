package com.englizya.internal_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.InternalRoutes
import com.englizya.model.model.RouteStations
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InternalSearchViewModel constructor(
    private val routeRepository: RouteRepository,


    ) : BaseViewModel() {

    private val _internalLines = MutableLiveData<List<InternalRoutes>>()
    val internalLines: MutableLiveData<List<InternalRoutes>>
        get() = _internalLines

    val stations: MutableList<List<RouteStations>> = ArrayList()
    private val _from = MutableLiveData<String>()
    val from: MutableLiveData<String>
        get() = _from

    private val _to = MutableLiveData<String>()
    val to: MutableLiveData<String>
        get() = _to

    fun getInternalRoutes(getOnlineForced: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        routeRepository
            .getInternalLines(getOnlineForced)
            .onSuccess {
                updateLoading(false)
                _internalLines.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun setFrom(from: String) {

    }

    fun setTo(to: String) {

    }
}