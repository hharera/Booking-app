package com.englizya.route

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.InternalRoutes
import com.englizya.route.adapter.CityAdapter
import com.englizya.route.adapter.CustomExpandableListAdapter
import com.englizya.route.adapter.ExpandableListData
import com.englizya.route.databinding.FragmentInternalRoutesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InternalRouteFragment : BaseFragment() {
    private lateinit var binding: FragmentInternalRoutesBinding
    private lateinit var adapter: CityAdapter

    private val internalRouteViewModel: RouteViewModel by sharedViewModel()


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

//        internalRouteViewModel.getInternalRoutes(false)
        setupObservers()
        setupUI()

        setUpListeners()
    }

    private fun setupUI() {
        adapter = CityAdapter(
            emptyList(),
            onItemClicked = {
                navigateToCityLine(it)
            }
        )
        binding.cityRecyclerView.adapter = adapter

    }

    private fun setUpListeners() {

//        binding.swipeLayout.setOnRefreshListener {
//            internalRouteViewModel.getInternalRoutes(true)
//            binding.swipeLayout.isRefreshing = false
//
//        }
    }

    private fun setupObservers() {
        internalRouteViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        internalRouteViewModel.internalLines.observe(viewLifecycleOwner) {result->
//            if (it.isEmpty()) {
//                Log.d("get Internal Routes ", "Remote")
//                internalRouteViewModel.getInternalRoutes(true)
//            }
            adapter.setCities(result.data!!)

        }
        internalRouteViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(it)
        }
    }

    private fun navigateToCityLine(cityName: String) {
        Log.d("Clicking To Navigate" , "click")

        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.INTERNAL_ROUTES_DETAILS,
                cityName
            )
        )
    }


    override fun onDestroyView() {
//        binding.swipeLayout.removeAllViews()
        super.onDestroyView()
    }

}