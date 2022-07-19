package com.englizya.internal_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.internal_search.databinding.FragmentInternalSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InternalSearchFragment : BaseFragment() {
    private lateinit var binding: FragmentInternalSearchBinding
    private val internalSearchViewModel: InternalSearchViewModel by viewModel()
    private var stationsDialog: StationsDialog? = null
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
        internalSearchViewModel.getInternalRoutes(true)
        setUpObservers()
        setUpListeners()

    }

    private fun setUpObservers() {
        internalSearchViewModel.internalLines.observe(viewLifecycleOwner) { list ->
            list.forEach {
                internalSearchViewModel.stations.add(it.routeStations)

            }

            stationsDialog = StationsDialog(
                stationsList = internalSearchViewModel.stations,
                adapter = StationsAdapter(internalSearchViewModel.stations.flatten())
            )
            Log.d("routeStations", internalSearchViewModel.stations.toString())
            stationsDialog!!.show(childFragmentManager, "StationsDialog")


        }

    }

    private fun setUpListeners() {
        binding.from.setOnClickListener {
            internalSearchViewModel.getInternalRoutes(true)
        }
        binding.to.setOnClickListener {
            internalSearchViewModel.getInternalRoutes(true)
        }
        binding.search.setOnClickListener {

            findNavController().navigate(
                NavigationUtils.getUriNavigation(
                    Domain.ENGLIZYA_PAY,
                    Destination.INTERNAL_SEARCH_RESULT,
                    false
                )
            )
        }
    }

}