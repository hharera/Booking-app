package com.englizya.announcement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.englizya.announcement.databinding.FragmentAnnouncementBinding
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Announcement
import com.englizya.repository.utils.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnnouncementsFragment : BaseFragment() {
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

//        announcementViewModel.getAnnouncements(false)
        setupListeners()
        setupObservers()
        setupUI()

    }

    private fun setupUI() {
        adapter = AnnouncementAdapter(
            emptyList(),
            onItemClicked = {
                navigateToAnnouncementDetails(it)
            }
        )
        binding.announcementRecyclerView.adapter = adapter

    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }

        announcementViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        announcementViewModel.getAnnouncements().observe(viewLifecycleOwner) {
            handleAnnouncementsResult(it)
        }
    }

    private fun handleAnnouncementsResult(result: Resource<List<Announcement>>) {
        when(result) {
            is Resource.Loading -> {
                handleLoading(true)
            }

            is Resource.Success -> {
                handleLoading(false)
                adapter.setAnnouncements(result.data!!)
            }

            is Resource.Error -> {
                handleLoading(false)
                handleFailure(result.error)
            }
        }
    }

    private fun navigateToAnnouncementDetails(announcement: Announcement) {
        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.ANNOUNCEMENT_DETAILS,
                announcement.announcementId
            )
        )
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
//        binding.announcementSwipeLayout.setOnRefreshListener {
////            announcementViewModel.getAnnouncements(true)
//            binding.announcementSwipeLayout.isRefreshing = false
//        }

    }
//
//    override fun onDestroyView() {
////        binding.announcementSwipeLayout.removeAllViews()
//        super.onDestroyView()
//    }
}