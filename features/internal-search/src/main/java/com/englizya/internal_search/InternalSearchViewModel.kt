package com.englizya.internal_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
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


//    private val _internalRoutes = MutableLiveData<List<InternalRoutes>>()
//    val internalRoutes: LiveData<List<InternalRoutes>> = _internalRoutes

    private val _sourceStationName = MutableLiveData<String>()
    val sourceStationName: MutableLiveData<String>
        get() = _sourceStationName

    private val _destinationStationName = MutableLiveData<String>()
    val destinationStationName: MutableLiveData<String>
        get() = _destinationStationName

    private val _searchResult = MutableLiveData<List<InternalRoutes>>()
    val searchResult: LiveData<List<InternalRoutes>> = _searchResult

    val internalRoutes = routeRepository.getInternalLines().asLiveData()


    init {
//        getInternalRoutes(true)
    }
//    fun getInternalRoutes(getOnlineForced: Boolean) = viewModelScope.launch(Dispatchers.IO) {
//        updateLoading(true)
//        routeRepository
//            .getInternalLines(getOnlineForced)
//            .onSuccess {
//                updateLoading(false)
//                _internalRoutes.postValue(it)
//            }
//            .onFailure {
//                updateLoading(false)
//                handleException(it)
//            }
//    }


    fun searchRoute() {
        Log.d("FromRoute", sourceStationName.value.toString())
        Log.d("ToRoute", destinationStationName.value.toString())


     _searchResult.value  =   internalRoutes.value?.data?.filter {
            it.routeStations.filter {
                it.stationName == sourceStationName.value ||
                        it.stationName == destinationStationName.value
            }.size == 2
        }

        Log.d("SearchRoute" , _searchResult.value .toString())


    }
}
