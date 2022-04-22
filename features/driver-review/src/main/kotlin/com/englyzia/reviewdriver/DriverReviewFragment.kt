package com.englyzia.reviewdriver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.ImageUtils
import com.englizya.driver_review.databinding.FragmentDriverReviewBinding
import com.opensooq.supernova.gligar.GligarPicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DriverReviewFragment : BaseFragment() {

    companion object {
        private const val IMAGE_REQ_CODE = 3005
    }

    private lateinit var binding: FragmentDriverReviewBinding
    private val driverReviewViewModel: DriverReviewViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDriverReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        driverReviewViewModel.insertionCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showDoneDialog()
                }
            }
        }

        driverReviewViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.submit.isEnabled = it.formIsValid

            if (it.driverCodeError != null) {
                binding.driverCode.error = getString(it.driverCodeError!!)
            } else if (it.reviewMessageError != null) {
                binding.message.error = getString(it.reviewMessageError!!)
            }
        }
    }

    private fun setupListeners() {
        binding.rating.setOnRatingBarChangeListener { ratingBar, rating, b ->
            driverReviewViewModel.setDriverRating(rating)
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.image.setOnClickListener {
            onImageClicked()
        }

        binding.driverCode.afterTextChanged {
            driverReviewViewModel.setDescription(it)
        }

        binding.message.afterTextChanged {
            driverReviewViewModel.setTitle(it)
        }

        binding.submit.setOnClickListener {
            lifecycleScope.launch {
                driverReviewViewModel.insertComplaint()
            }
        }
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
        binding.message.setText(driverReviewViewModel.message.value)
        binding.image.setImageBitmap(driverReviewViewModel.image.value)
        binding.driverCode.setText(driverReviewViewModel.driverCode.value)
    }

    private fun onImageClicked() {
        GligarPicker()
            .requestCode(IMAGE_REQ_CODE)
            .limit(1)
            .disableCamera(true)
            .withFragment(this)
            .show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = ImageUtils.convertImagePathToBitmap(data)
            imageBitmap?.let {
                driverReviewViewModel.setImage(it)
            }
        }
    }

}