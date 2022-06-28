package com.englizya.announcement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.englizya.announcement.R
import com.englizya.announcement.databinding.FragmentAnnouncementBinding
import com.englizya.common.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnnouncementFragment : BaseFragment() {
    private lateinit var binding: FragmentAnnouncementBinding
    private lateinit var adapter: AnnouncementAdapter
    private val announcementViewModel: AnnouncementViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAnnouncementBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupUI()

    }
    private fun setupUI(){
        adapter = AnnouncementAdapter(
            emptyList(),
        )
        binding.announcementRecyclerView.adapter = adapter

    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }



        announcementViewModel.announcements.observe(viewLifecycleOwner) {
            if(it != null){
                adapter.setAnnouncements(it)

            }
            Log.d("offers", it.toString())
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener{
            findNavController().popBackStack()
        }

    }
}