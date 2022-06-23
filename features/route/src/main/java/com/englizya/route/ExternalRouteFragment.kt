package com.englizya.route

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.LineDetails
import com.englizya.route.adapter.CustomExpandableListAdapter
import com.englizya.route.adapter.ExpandableListData1
import com.englizya.route.databinding.FragmentExternalRoutesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExternalRouteFragment : BaseFragment() {
    private lateinit var binding: FragmentExternalRoutesBinding
    private val externalRoutesViewModel: ExternalRouteViewModel by viewModel()

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
        binding = FragmentExternalRoutesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  setUpAdapter()
        externalRoutesViewModel.getExternalRoutes()
        setupObservers()

    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupObservers() {
        externalRoutesViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        externalRoutesViewModel.externalLines.observe(viewLifecycleOwner) {
            setData(it)
        }
        externalRoutesViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun setData(lineList: List<LineDetails>?) {
        lineList?.forEach {
            ExpandableListData1.setData(it.lineName , it.linePath.branch?.branchName.toString())
        }

        setUpAdapter()

    }

    private fun setUpAdapter() {
        val listData = ExpandableListData1.getData()
        titleList = ArrayList(listData.keys)
        adapter =
            CustomExpandableListAdapter(context!!, titleList as ArrayList<String>, listData)
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