package com.englizya.route

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.local.ExternalRoutes.ExternalRoutesDatabase
import com.englizya.local.InternalRoutes.InternalRoutesDatabase
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.InternalRoutes
import com.englizya.repository.RouteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RouteViewModel constructor(
    private val routeRepository: RouteRepository,
    private val internalRouteDatabase: InternalRoutesDatabase,
    private val externalRoutesDatabase: ExternalRoutesDatabase,

    ) : BaseViewModel() {

    private val _externalLines = MutableLiveData<List<ExternalRoutes>>()
    val externalLines: MutableLiveData<List<ExternalRoutes>>
        get() = _externalLines

    private val _internalLines = MutableLiveData<List<InternalRoutes>>()
    val internalLines: MutableLiveData<List<InternalRoutes>>
        get() = _internalLines

    fun getExternalRoutes() = viewModelScope.launch {
        updateLoading(true)
        routeRepository
            .getExternalLines()
            .onSuccess {
                viewModelScope.launch(Dispatchers.IO) {
                    externalRoutesDatabase.getMarketDao().insertExternalRoutes(it)
                    Log.d(
                        "LocalInternalRoutes",
                        externalRoutesDatabase.getMarketDao().getExternalRoutes().toString()
                    )
                }
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
                viewModelScope.launch(Dispatchers.IO) {
                    internalRouteDatabase.getMarketDao().insertInternalRoutes(it)
                    Log.d(
                        "LocalInternalRoutes",
                        internalRouteDatabase.getMarketDao().getInternalRoutes().toString()
                    )
                }
                updateLoading(false)
                _internalLines.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    private fun getInternalRouteLocal() {
        internalRouteDatabase.getMarketDao().getInternalRoutes().let {
            _internalLines.postValue(it)
        }
    }
    private fun getExternalRouteLocal() {
        externalRoutesDatabase.getMarketDao().getExternalRoutes().let {
            _externalLines.postValue(it)
        }
    }
}