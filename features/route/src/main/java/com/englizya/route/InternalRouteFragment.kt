package com.englizya.route

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.InternalRoutes
import com.englizya.route.adapter.CustomExpandableListAdapter
import com.englizya.route.adapter.ExpandableListData
import com.englizya.route.databinding.FragmentInternalRoutesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InternalRouteFragment : BaseFragment() {
    private lateinit var binding: FragmentInternalRoutesBinding
    private val internalRouteViewModel: RouteViewModel by viewModel()

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

        internalRouteViewModel.getInternalRoutes(false)
        setupObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.swipeLayout.setOnRefreshListener {

            internalRouteViewModel.getInternalRoutes(true)
            binding.swipeLayout.isRefreshing = false

        }
    }

    private fun setupObservers() {
        internalRouteViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        internalRouteViewModel.internalLines.observe(viewLifecycleOwner) {
            setData(it)
            setUpAdapter()

        }
        internalRouteViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun setData(lineList: List<InternalRoutes>?) {
        ExpandableListData.setInternalRoutesData(lineList)
        Log.d("Internal Routes", lineList.toString())

    }

    private fun setUpAdapter() {
        val routeDetails = ExpandableListData.routeDetails
        titleList = ExpandableListData.title
        adapter =
            CustomExpandableListAdapter(context!!, titleList as ArrayList<String>, routeDetails)
        binding.internalLV.setAdapter(adapter)
        binding.internalLV.setOnGroupExpandListener { groupPosition ->

        }
        binding.internalLV.setOnGroupCollapseListener { groupPosition ->

        }
        binding.internalLV.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->

            false
        }
    }

    override fun onDestroyView() {
        binding.swipeLayout.removeAllViews()
        super.onDestroyView()
    }

}