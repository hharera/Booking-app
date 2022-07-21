package com.englizya.internal_search.dialogs

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.englizya.internal_search.StationsAdapter
import com.englizya.internal_search.databinding.DialogStationsBinding
import com.englizya.model.model.RouteStations

class SourceStationsDialog constructor(
    private var stationsList: List<List<RouteStations>>,
    private var adapter: StationsAdapter,
    private val onSourceStationClicked: (RouteStations) -> Unit,

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
        setupUI()
        setUpListeners()


    }

    private fun setupUI() {
        adapter = StationsAdapter(emptyList(),
            onItemClicked = {
                onSourceStationClicked(it)
        }
        )
        binding.stations.adapter = adapter
    }


    private fun setUpListeners() {
        Log.d("Stations", stationsList.toString())
        adapter.setStations(stationsList)

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Text", s.toString() + count.toString())
                if (count > 0) {
                    adapter.filter(s.toString())

                } else {
                    adapter.setStations(stationsList)
                }
            }
        })


    }

}