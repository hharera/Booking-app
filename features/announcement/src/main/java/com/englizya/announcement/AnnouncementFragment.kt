package com.englizya.announcement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.englizya.announcement.R
import com.englizya.announcement.databinding.FragmentAnnouncementBinding
import com.englizya.common.base.BaseFragment
import com.englizya.common.utils.navigation.Destination
import com.englizya.common.utils.navigation.Domain
import com.englizya.common.utils.navigation.NavigationUtils
import com.englizya.model.model.Announcement
import com.englizya.repository.utils.Resource
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
        announcementViewModel.announcements.observe(viewLifecycleOwner) {
                result->

                adapter.setAnnouncements(result.data!!)
            Log.d("Announcement", result.data.toString())

            binding.progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
            binding.errorText.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
       binding.errorText.text = result.error?.localizedMessage
        }
    }

    private fun navigateToAnnouncementDetails(announcement: Announcement) {

        findNavController().navigate(
            NavigationUtils.getUriNavigation(
                Domain.ENGLIZYA_PAY,
                Destination.ANNOUNCEMENT_DETAILS,
                announcement.announcementId.toString()
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