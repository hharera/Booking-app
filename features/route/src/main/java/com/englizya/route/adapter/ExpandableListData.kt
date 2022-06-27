package com.englizya.route.adapter

import android.util.Log
import com.englizya.model.model.RouteStations
import com.englizya.model.model.Routes
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

internal object ExpandableListData {
    var title:
            List<String> = emptyList()
        get() {
            return field
        }

    var routeDetails:
            MutableList<List<RouteStations>> = ArrayList()
        get() {
            return field
        }

    fun setData(routeList: List<Routes>?) {
        val titleList: MutableList<String> = ArrayList()
        val stations: MutableList<List<RouteStations>> = ArrayList()
        routeList?.forEach { route ->
            titleList.add(route.routeName)

           stations.add(route.routeStations.sortedBy { it.stationOrder })

        }

        Log.d("Station ", stations.toString())

        title = titleList
        routeDetails = stations

    }
}

