package com.englizya.route

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.Routes
import com.englizya.route.adapter.CustomExpandableListAdapter
import com.englizya.route.adapter.ExpandableListData
import com.englizya.route.databinding.FragmentExternalRoutesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExternalRouteFragment : BaseFragment() {
    private lateinit var binding: FragmentExternalRoutesBinding
    private val externalRoutesViewModel: RouteViewModel by viewModel()

    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentExternalRoutesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  setUpAdapter()
        externalRoutesViewModel.getExternalRoutes()
        setupObservers()

    }

//    override fun onResume() {
//        super.onResume()
//        externalRoutesViewModel.getExternalRoutes()
//
//    }

    private fun setupObservers() {
        externalRoutesViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        externalRoutesViewModel.externalLines.observe(viewLifecycleOwner) {
            setData(it)
            setUpAdapter()

        }
        externalRoutesViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun setData(lineList: List<Routes>?) {
        ExpandableListData.setData(lineList)
        Log.d("External Routes", lineList.toString())

    }



    private fun setUpAdapter() {
        val routeDetails = ExpandableListData.routeDetails
        titleList = ExpandableListData.title
        adapter =
            CustomExpandableListAdapter(context!!, titleList as ArrayList<String>, routeDetails)
        binding.externalLV.setAdapter(adapter)
        binding.externalLV.setOnGroupExpandListener { groupPosition ->

        }
        binding.externalLV.setOnGroupCollapseListener { groupPosition ->

        }
        binding.externalLV.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

            false
        }
    }

}