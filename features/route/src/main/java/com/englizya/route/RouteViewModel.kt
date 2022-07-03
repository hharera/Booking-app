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
//    private val internalRouteDatabase: InternalRoutesDatabase,
//    private val externalRoutesDatabase: ExternalRoutesDatabase,

    ) : BaseViewModel() {

    private val _externalLines = MutableLiveData<List<ExternalRoutes>>()
    val externalLines: MutableLiveData<List<ExternalRoutes>>
        get() = _externalLines

    private val _internalLines = MutableLiveData<List<InternalRoutes>>()
    val internalLines: MutableLiveData<List<InternalRoutes>>
        get() = _internalLines

    fun getExternalRoutes(getOnlineForced: Boolean) = viewModelScope.launch (Dispatchers.IO){
        updateLoading(true)
        routeRepository
            .getExternalLines(getOnlineForced)
            .onSuccess {
//                viewModelScope.launch(Dispatchers.IO) {
//                    externalRoutesDatabase.getMarketDao().insertExternalRoutes(it)
//                }
                updateLoading(false)
                _externalLines.postValue( it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun getInternalRoutes(getOnlineForced:Boolean) = viewModelScope.launch (Dispatchers.IO){
        updateLoading(true)
        routeRepository
            .getInternalLines(getOnlineForced)
            .onSuccess {
//                viewModelScope.launch(Dispatchers.IO) {
//                    internalRouteDatabase.getMarketDao().insertInternalRoutes(it)
//                }
                updateLoading(false)
                _internalLines.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

//    fun getLocalInternalRoute() {
//        viewModelScope.launch(Dispatchers.IO) {
//            internalRouteDatabase.getMarketDao().getInternalRoutes().let {
//                Log.d("Local", it.toString())
//                _internalLines.postValue(it)
//            }
//        }
//
//    }
//
//    fun getLocalExternalRoute() {
//        externalRoutesDatabase.getMarketDao().getExternalRoutes().let {
//            _externalLines.postValue(it)
//        }
//    }
}