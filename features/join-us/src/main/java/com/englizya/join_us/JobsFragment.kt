package com.englizya.join_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.join_us.databinding.FragmentJobsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class JobsFragment : BaseFragment() {
    private lateinit var binding : FragmentJobsBinding
    private lateinit var adapter: JobsAdapter
    private val jobsViewModel: JobsViewModel  by viewModel()
    private val jobItemList = arrayListOf(
        JobItem.Driver,

    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentJobsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpObservers()
        setUpAdapter()
    }

    private fun setUpAdapter() {
        JobsAdapter(jobItemList) {
            checkClickItem(it)
        }.let { jobsAdapter ->
            adapter = jobsAdapter
            binding.jobsRecyclerView.adapter = jobsAdapter
        }
    }

    private fun checkClickItem(jobItem: JobItem) {
        when (jobItem) {
            is JobItem.Driver -> {
            }
            else -> {}
        }
    }

    private fun setUpObservers() {
    }

    private fun setUpListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}