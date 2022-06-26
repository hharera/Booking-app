package com.englizya.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import com.englizya.common.base.BaseFragment
import com.englizya.route.adapter.CustomExpandableListAdapter
import com.englizya.route.adapter.ExpandableListData.data
import com.englizya.route.databinding.FragmentInternalRoutesBinding

class InternalRouteFragment : BaseFragment() {
    private lateinit var binding : FragmentInternalRoutesBinding
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentInternalRoutesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setUpAdapter()
    }

    override fun onResume() {
        super.onResume()
    }

//    private fun setUpAdapter() {
//        val listData = data
//       titleList =  listData.map{
//            it.first
//        }
//        adapter =
//            CustomExpandableListAdapter(context!!, titleList as ArrayList<String>, listData)
//        binding.internalLV .setAdapter(adapter)
//        binding.internalLV .setOnGroupExpandListener { groupPosition ->
//
//        }
//        binding.internalLV .setOnGroupCollapseListener { groupPosition ->
//
//        }
//        binding.internalLV .setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
//
//            false
//        }
//    }
}