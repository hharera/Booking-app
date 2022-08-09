package com.englizya.internal_search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.internal_search.adapter.CustomExpandableListAdapter
import com.englizya.internal_search.adapter.ExpandableListData
import com.englizya.internal_search.databinding.FragmentInternalSearchResultBinding
import com.englizya.model.model.InternalRoutes
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class InternalSearchResultFragment : BaseFragment() {
    private val internalSearchViewModel: InternalSearchViewModel by sharedViewModel()
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null
    private var lineCodeList: List<String>? = null
    private lateinit var binding: FragmentInternalSearchResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentInternalSearchResultBinding.inflate(layoutInflater)

        changeStatusBarColor(R.color.grey_100)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun setUpObservers() {
        internalSearchViewModel.searchResult.observe(viewLifecycleOwner) {
            Log.d("SearchResultFragment", it.map { it.lineCode }.toString())
            setData(it)
            setUpAdapter()
        }

    }

    private fun setData(lineList: List<InternalRoutes>?) {
        ExpandableListData.setInternalRoutesData(lineList)
        Log.d("Internal Routes", lineList.toString())

    }

    private fun setUpAdapter() {
        val routeDetails = ExpandableListData.routeDetails
        titleList = ExpandableListData.title
        lineCodeList = ExpandableListData.lineCode
        Log.d("lineCode", lineCodeList.toString())

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