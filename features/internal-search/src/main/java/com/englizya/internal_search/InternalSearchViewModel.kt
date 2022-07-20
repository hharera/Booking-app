package com.englizya.internal_search

import android.util.Log
import androidx.lifecycle.LiveData
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
    val stations: MutableList<List<RouteStations>> = ArrayList()


    private val _from = MutableLiveData<List<InternalRoutes>>()
    val from: LiveData<List<InternalRoutes>> = _from


    private val _to = MutableLiveData<List<InternalRoutes>>()
    val to: LiveData<List<InternalRoutes>> = _to


    private val _fromRouteStation = MutableLiveData<String>()
    val fromRouteStation: MutableLiveData<String>
        get() = _fromRouteStation

    private val _toRouteStation = MutableLiveData<String>()
    val toRouteStation: MutableLiveData<String>
        get() = _toRouteStation

    private val _searchResult = MutableLiveData<List<InternalRoutes>>()
    val searchResult: LiveData<List<InternalRoutes>> = _searchResult

    fun getFromInternalRoutes(getOnlineForced: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        routeRepository
            .getInternalLines(getOnlineForced)
            .onSuccess {
                updateLoading(false)
                _from.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun getToInternalRoutes(getOnlineForced: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        routeRepository
            .getInternalLines(getOnlineForced)
            .onSuccess {
                updateLoading(false)
                _to.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun searchRoute() {
        Log.d("FromRoute", fromRouteStation.value.toString())
        Log.d("ToRoute", toRouteStation.value.toString())

     _searchResult.value  =   from.value?.filter {
            it.routeStations.filter {
                it.stationName == fromRouteStation.value ||
                        it.stationName == toRouteStation.value
            }.size == 2
        }

        Log.d("SearchRoute" , _searchResult.value .toString())


    }
}
//        if (fromRouteStation.value?.stationOrder!! < toRouteStation.value?.stationOrder!!) {
//            _searchResult.value = from.value?.filter {
//
//                it.routeStations.contains(fromRouteStation.value) && it.routeStations.contains(
//                    toRouteStation.value
//                )
//            }.also { internalRoutes ->
//                internalRoutes?.map { internalRoute -> internalRoute.routeStations.sortedBy { it.stationOrder } }
//            }
//
//        } else {
//            _searchResult.value = from.value?.filter {
//                it.routeStations.contains(fromRouteStation.value) && it.routeStations.contains(
//                    toRouteStation.value
//                )
//            }.also { internalRoutes ->
//                internalRoutes?.map { internalRoute -> internalRoute.routeStations.sortedByDescending { it.stationOrder } }
//
//            }
//        }
//Log.d("SearchRoute" , _searchResult.value .toString())
//}
//}