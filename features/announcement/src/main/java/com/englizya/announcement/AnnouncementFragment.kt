package com.englizya.announcement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.englizya.announcement.constants.ImagesConstants
import com.englizya.announcement.databinding.FragmentAnnouncementDetailsBinding
import com.englizya.common.base.BaseFragment
import com.englizya.model.model.Announcement
import com.englizya.repository.utils.Resource
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class AnnouncementFragment : BaseFragment() {

    private lateinit var binding: FragmentAnnouncementDetailsBinding
    private val announcementViewModel: AnnouncementViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getInt("announcementId")?.let {
            announcementViewModel.getAnnouncement(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentAnnouncementDetailsBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.visibility = View.INVISIBLE

        setupListeners()
        setupObservers()

    }

    private fun updateUI(announcement: Announcement) {
        Picasso.get().load(ImagesConstants.IMAGE_URL + announcement.announcementImageUrl)
            .into(binding.announcementImg)
        binding.announcementDetails.text = announcement.announcementDescription
        binding.announcementTitle.text = announcement.announcementTitle
    }

    private fun setupObservers() {
        connectionLiveData.observe(viewLifecycleOwner) {
            showInternetSnackBar(binding.root, it)
        }
        announcementViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        announcementViewModel.announcement.observe(viewLifecycleOwner) {
            handleResult(it)
        }
    }

    private fun handleResult(resource: Resource<Announcement>) {
        when (resource) {
            is Resource.Loading -> {
                handleLoading(true)
            }

            is Resource.Success -> {
                handleLoading(false)
                resource.data?.let { updateUI(it) }
            }

            is Resource.Error -> {
                handleLoading(false)
                handleFailure(resource.error)
                Toast.makeText(context, getString(R.string.error_toast), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}