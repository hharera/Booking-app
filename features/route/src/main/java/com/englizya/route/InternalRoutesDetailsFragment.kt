package com.englizya.route

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.InternalRoutes
import com.englizya.route.adapter.CustomExpandableListAdapter
import com.englizya.route.adapter.ExpandableListData
import com.englizya.route.databinding.FragmentInternalRoutesDetailsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InternalRoutesDetailsFragment : BaseFragment() {
    private lateinit var binding: FragmentInternalRoutesDetailsBinding
    private val internalRouteViewModel: RouteViewModel by sharedViewModel()
    private lateinit var cityName: String

    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    private var lineCodeList: List<String>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentInternalRoutesDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("cityName").let {
            if (it != null) {
                cityName = it

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpListeners()
        updateUI()

    }

    private fun setUpListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.swipeLayout.setOnRefreshListener {
//            internalRouteViewModel.getInternalRoutes(true)
//            binding.swipeLayout.isRefreshing = false
//        }
    }

    private fun updateUI() {
        binding.cityName.text = cityName
    }

    private fun setUpObservers() {
        internalRouteViewModel.internalLines.observe(viewLifecycleOwner) {
//            if (it.isEmpty()) {
//                Log.d("get Internal Routes ", "Remote")
//                internalRouteViewModel.getInternalRoutes(true)
//            }
            setData(it.data, cityName)
            setUpAdapter()


        }
        internalRouteViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }
        internalRouteViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }

    }

    private fun setData(lineList: List<InternalRoutes>?, cityName: String) {
        ExpandableListData.setInternalRoutesData(lineList, cityName)
        Log.d("Internal Routes", lineList.toString())

    }

    private fun setUpAdapter() {
        val routeDetails = ExpandableListData.routeDetails
        titleList = ExpandableListData.title
        lineCodeList = ExpandableListData.lineCode
        adapter =
            CustomExpandableListAdapter(
                context!!,
                lineCodeList as ArrayList<Int>,
                titleList as ArrayList<String>,
                routeDetails
            )
        binding.internalLV.setAdapter(adapter)

    }
}