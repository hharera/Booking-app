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
import com.englizya.internal_search.dialogs.SourceStationsDialog
import com.englizya.internal_search.dialogs.DestinationStationsDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InternalSearchFragment : BaseFragment() {
    private lateinit var binding: FragmentInternalSearchBinding
    private val internalSearchViewModel: InternalSearchViewModel by sharedViewModel()
    private var sourceStationsDialog: SourceStationsDialog? = null
    private var destinationStationsDialog: DestinationStationsDialog? = null

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
        setUpObservers()
        setUpListeners()

    }

    private fun setUpObservers() {
        internalSearchViewModel.internalRoutes.observe(viewLifecycleOwner) { list ->
            internalSearchViewModel.stations.clear()

            list.forEach {
                internalSearchViewModel.stations.add(it.routeStations)

            }


        }
    }

    private fun onDestinationStationCLicked(stationName: String) {
        binding.destination.setText(stationName)
        internalSearchViewModel.destinationStationName.value = stationName
        destinationStationsDialog!!.dismiss()

    }

    private fun onSourceStationCLicked(stationName: String) {
        binding.source.setText(stationName)
        internalSearchViewModel.sourceStationName.value = stationName
        sourceStationsDialog!!.dismiss()

    }

    private fun setUpListeners() {
        binding.source.setOnClickListener {
//            internalSearchViewModel.getInternalRoutes(true)

            sourceStationsDialog = SourceStationsDialog(
                stationsList = internalSearchViewModel.stations,
                adapter = StationsAdapter(
                    internalSearchViewModel.stations.flatten(),
                    onItemClicked = {
                        onSourceStationCLicked(it.stationName)

                    }),
                onSourceStationClicked = {
                    onSourceStationCLicked(it.stationName)

                }
            )
            Log.d("routeStations", internalSearchViewModel.stations.toString())
            sourceStationsDialog!!.show(childFragmentManager, "StationsDialog")

        }
        binding.destination.setOnClickListener {
//            internalSearchViewModel.getInternalRoutes(true)

            destinationStationsDialog = DestinationStationsDialog(
                stationsList = internalSearchViewModel.stations,
                adapter = StationsAdapter(
                    internalSearchViewModel.stations.flatten(),
                    onItemClicked = {
                        onDestinationStationCLicked(it.stationName)

                    }),
                onDestinationStationClicked = {
                    onDestinationStationCLicked(it.stationName)
                }
            )
            Log.d("routeStations", internalSearchViewModel.stations.toString())
            destinationStationsDialog!!.show(childFragmentManager, "StationsDialog")


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