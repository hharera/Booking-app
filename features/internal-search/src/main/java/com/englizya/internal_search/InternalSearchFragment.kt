package com.englizya.internal_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.internal_search.databinding.FragmentInternalSearchBinding
import com.englizya.model.model.RouteStations
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InternalSearchFragment : BaseFragment() {
    private lateinit var binding: FragmentInternalSearchBinding
    private val internalSearchViewModel: InternalSearchViewModel by sharedViewModel()
    private var fromStationsDialog: FromStationsDialog? = null
    private var toStationsDialog: ToStationsDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentInternalSearchBinding.inflate(layoutInflater)

        changeStatusBarColor(R.color.grey_100)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        internalSearchViewModel.getInternalRoutes(true)
        setUpObservers()
        setUpListeners()

    }

    private fun setUpObservers() {
        internalSearchViewModel.from.observe(viewLifecycleOwner) { list ->
            list.forEach {
                internalSearchViewModel.stations.add(it.routeStations)

            }
            fromStationsDialog = FromStationsDialog(
                stationsList = internalSearchViewModel.stations,
                adapter = StationsAdapter(
                    internalSearchViewModel.stations.flatten(),
                    onItemClicked = {
                        onFromStationCLicked(it.stationName, it)

                    }),
                onFromStationClicked = {
                    onFromStationCLicked(it.stationName, it)

                }
            )
            Log.d("routeStations", internalSearchViewModel.stations.toString())
            fromStationsDialog!!.show(childFragmentManager, "StationsDialog")


        }

        internalSearchViewModel.to.observe(viewLifecycleOwner) { list ->
            list.forEach {
                internalSearchViewModel.stations.add(it.routeStations)

            }
            toStationsDialog = ToStationsDialog(
                stationsList = internalSearchViewModel.stations,
                adapter = StationsAdapter(
                    internalSearchViewModel.stations.flatten(),
                    onItemClicked = {
                        onToStationCLicked(it.stationName, it)

                    }),
                onToStationClicked = {
                    onToStationCLicked(it.stationName, it)
                }
            )
            Log.d("routeStations", internalSearchViewModel.stations.toString())
            toStationsDialog!!.show(childFragmentManager, "StationsDialog")


        }


    }

    private fun onToStationCLicked(stationName: String, routeStation: RouteStations) {
        binding.to.setText(stationName)
        internalSearchViewModel.toRouteStation.value = stationName
        toStationsDialog!!.dismiss()

    }

    private fun onFromStationCLicked(stationName: String, routeStation: RouteStations) {
        binding.from.setText(stationName)
        internalSearchViewModel.fromRouteStation.value = stationName
        fromStationsDialog!!.dismiss()

    }

    private fun setUpListeners() {
        binding.from.setOnClickListener {
            internalSearchViewModel.getFromInternalRoutes(true)
        }
        binding.to.setOnClickListener {
            internalSearchViewModel.getToInternalRoutes(true)
        }
        binding.search.setOnClickListener {
            internalSearchViewModel.searchRoute().also {
                navigateToSearchResult()

            }


        }
    }

    private fun navigateToSearchResult() {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.INTERNAL_SEARCH_RESULT,
                false
            )
        )
    }

}