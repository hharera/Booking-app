package com.englizya.route.adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.englizya.model.model.RouteStations
import com.englizya.route.R
import java.util.HashMap

class CustomExpandableListAdapter internal constructor(
    private val context: Context,
    private val lineCodeList: List<Int>,
    private val titleList: List<String>,
    private val RoutesList: MutableList<List<RouteStations>>
) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return RoutesList[listPosition][expandedListPosition].stationName
//        [this.lineList[listPosition]][expandedListPosition]
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.card_view_line_routes, null)
        }
        val expandedListTextView = convertView!!.findViewById<TextView>(R.id.lineDetailTxt)
        expandedListTextView.text =  expandedListText
        return convertView
    }

    override fun getChildrenCount(listPosition: Int): Int {
        return this.RoutesList[listPosition].size
    }

    override fun getGroup(listPosition: Int): Any {
        return this.titleList[listPosition]
    }

    fun getLineCode(listPosition: Int): Any {
        return this.lineCodeList[listPosition]
    }

    override fun getGroupCount(): Int {
        return this.titleList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val listLineCode = getLineCode(listPosition) as String
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater =
                this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.card_view_route_category, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.lineTitleTxt)
        val listLineCodeTextView = convertView!!.findViewById<TextView>(R.id.lineCodeTxt)
//        listLineCodeTextView.setTypeface(listLineCodeTextView.typeface, Typeface.BOLD)
        listLineCodeTextView.text = context.getString(R.string.line_code) + " " + listLineCode
        listTitleTextView.setTypeface(listTitleTextView.typeface, Typeface.BOLD)
        listTitleTextView.text = listTitle
        return convertView
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}