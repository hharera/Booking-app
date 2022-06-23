package com.englizya.route.adapter

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object ExpandableListData1{
    private val expandableListDetail = HashMap<String, List<String>>()

    fun getData() : HashMap<String, List<String>>{
        return expandableListDetail

    }
    fun setData(title : String,station:String){
        val titleList : MutableList<String> = ArrayList()
        titleList.add(title)

        val stationList : MutableList<String> = ArrayList()
        stationList.add(station)


        titleList.forEach {
            expandableListDetail[it] = stationList
        }


    }
}
internal object ExpandableListData {
    val data: HashMap<String, List<String>>
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


            val title : MutableList<String> = ArrayList()
            title.add("firstLine")
            title.add("secondLine")

            title.forEach {
                expandableListDetail[it] = firstLine

            }
//            expandableListDetail[] = firstLine
//            expandableListDetail["Second Line"] = secondLine
            return expandableListDetail
        }
}