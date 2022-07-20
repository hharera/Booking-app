package com.englizya.internal_search.adapter

import android.util.Log
import com.englizya.model.model.ExternalRoutes
import com.englizya.model.model.RouteStations
import com.englizya.model.model.InternalRoutes
import kotlin.collections.ArrayList

internal object ExpandableListData {
    var title:
            List<String> = emptyList()
        get() {
            return field
        }
    var lineCode:
            List<String> = emptyList()
        get() {
            return field
        }

    var routeDetails:
            MutableList<List<RouteStations>> = ArrayList()
        get() {
            return field
        }

    fun setInternalRoutesData(routeList: List<InternalRoutes>?) {
        val lineCodeList: MutableList<String> = ArrayList()

        val titleList: MutableList<String> = ArrayList()
        val stations: MutableList<List<RouteStations>> = ArrayList()
        routeList?.forEach { route ->
            titleList.add(route.routeName)
            lineCodeList.add(route.lineCode)

           stations.add(route.routeStations.sortedBy { it.stationOrder })

        }

        Log.d("Station ", stations.toString())

        lineCode = lineCodeList
        title = titleList
        routeDetails = stations

    }
    fun setExternalRoutesData(routeList: List<ExternalRoutes>?) {
        val lineCodeList: MutableList<String> = ArrayList()
        val titleList: MutableList<String> = ArrayList()
        val stations: MutableList<List<RouteStations>> = ArrayList()
        routeList?.forEach { route ->
            titleList.add(route.routeName)
            lineCodeList.add(route.lineCode)

            stations.add(route.routeStations.sortedBy { it.stationOrder })

        }

        Log.d("Station ", stations.toString())

        lineCode = lineCodeList
        title = titleList
        routeDetails = stations

    }
}

