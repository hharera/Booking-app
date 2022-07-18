package com.englizya.internal_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import com.englizya.common.base.BaseFragment
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
        internalSearchViewModel.internalLines.observe(viewLifecycleOwner) {

            stationsDialog = StationsDialog(stationsList = it, adapter = StationsAdapter(it))
            stationsDialog!!.show(childFragmentManager, "StationsDialog")


        }
    }

    private fun setUpListeners() {
        binding.from.setOnClickListener {
            internalSearchViewModel.getInternalRoutes(true)
        }
    }

}