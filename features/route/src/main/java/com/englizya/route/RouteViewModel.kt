package com.englizya.route

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel constructor(
    private val routeRepository: RouteRepository,


    ) : BaseViewModel() {

    private val _externalLines = MutableLiveData<List<ExternalRoutes>>()
    val externalLines: MutableLiveData<List<ExternalRoutes>>
        get() = _externalLines

    private val _internalLines = MutableLiveData<List<InternalRoutes>>()
    val internalLines: MutableLiveData<List<InternalRoutes>>
        get() = _internalLines

    fun getExternalRoutes(getOnlineForced: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        routeRepository
            .getExternalLines(getOnlineForced)
            .onSuccess {
                updateLoading(false)
                _externalLines.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

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
}