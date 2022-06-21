package com.englizya.route.adapter

import java.util.*
internal object ExpandableListData {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail =
                HashMap<String, List<String>>()
            val firstLine: MutableList<String> =
                ArrayList()
            firstLine.add("القاهرة")
            firstLine.add("بلبيس")
            firstLine.add("الزقازيق")


            val secondLine: MutableList<String> = ArrayList()
            secondLine.add("القاهرة 1")
            secondLine.add("بلبيس 1 ")
            secondLine.add("الزقازيق 1 ")

            expandableListDetail["First Line"] = firstLine
            expandableListDetail["Second Line"] = secondLine
            return expandableListDetail
        }
}