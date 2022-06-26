package com.englizya.route.adapter

import android.util.Log
import com.englizya.model.model.RouteStations
import com.englizya.model.model.Routes
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

internal object ExpandableListData1 {
    var data1:
            List<Pair<String, MutableList<List<RouteStations>>>> = emptyList()
        get() {
            return field
        }

    fun setData(routeList: List<Routes>?) {
        val titleList: MutableList<String> = ArrayList()
        val stations: MutableList<List<RouteStations>> = ArrayList()

        val stationName: MutableList<String> = ArrayList()

        routeList?.forEach { route ->
            titleList.add(route.routeName)
            stations.add(route.routeStations)
//            route.routeStations.sortedBy { it.stationOrder }.map {
//                stationName.add(it.stationName)
//            }
//
        }
        data1 = titleList.map {
            Pair(it, stations)
        }


//        routeList?.forEach {
//            it.routeStations.map {
//                stationName.add(it.stationName)
//            }
//        }
        Log.d("Station ", stations.toString())
        Log.d("Station ", data1.toString())

//        stationName.forEach {
//            it.forEach { routeSations ->
//
//            }
//
//        }


    }
}


internal object ExpandableListData {
    val data: List<Pair<String, MutableList<String>>>
        get() {
            val expandableListDetail = HashMap<String, List<String>>()
            val firstLine: MutableList<String> =
                ArrayList()
            firstLine.add("القاهرة")
            firstLine.add("بلبيس")
            firstLine.add("الزقازيق")
            firstLine.add("القاهرة")
            firstLine.add("بلبيس")
            firstLine.add("الزقازيق")


            val title: MutableList<String> = ArrayList()
            title.add("firstLine")
            title.add("firstLine")
            return title.map {
                Pair(it, firstLine)
            }

//            expandableListDetail[] = firstLine
//            expandableListDetail["Second Line"] = secondLine
            //   return expandableListDetail
        }
}