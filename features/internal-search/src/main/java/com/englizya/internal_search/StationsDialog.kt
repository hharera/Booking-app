package com.englizya.internal_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.internal_search.databinding.DialogStationsBinding
import com.englizya.model.model.InternalRoutes

class StationsDialog constructor(
    private var stationsList: List<InternalRoutes>,
    private var adapter: StationsAdapter

) : DialogFragment() {
    private lateinit var binding: DialogStationsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DialogStationsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()
        setUpListeners()


    }

    private fun updateUI() {
        adapter = StationsAdapter(emptyList())

        binding.stations.adapter = adapter
    }


    private fun setUpListeners() {
        Log.d("Stations" , stationsList.toString())
        adapter.setStations(stationsList)


    }
}