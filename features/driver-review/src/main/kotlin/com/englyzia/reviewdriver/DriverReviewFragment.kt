package com.englyzia.reviewdriver

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.imagepickerlibrary.ImagePickerActivityClass
import com.app.imagepickerlibrary.ImagePickerBottomsheet
import com.app.imagepickerlibrary.bottomSheetActionCamera
import com.app.imagepickerlibrary.bottomSheetActionGallary
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.ImageUtils
import com.englizya.driver_review.databinding.FragmentDriverReviewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DriverReviewFragment : BaseFragment(), ImagePickerActivityClass.OnResult,
    ImagePickerBottomsheet.ItemClickListener {

    companion object {
        private const val IMAGE_REQ_CODE = 3005
    }

    private lateinit var imagePicker: ImagePickerActivityClass
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
        driverReviewViewModel.image.observe(viewLifecycleOwner) {
            binding.image.setImageBitmap(it)
        }

        driverReviewViewModel.insertionCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showDoneDialog()
                    findNavController().popBackStack()
                }
            }
        }

        driverReviewViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.submit.isEnabled = it.formIsValid

            if (it.driverCodeError != null) {
                binding.textInputLayoutDriverCode.error = getString(it.driverCodeError!!)
            } else {
                binding.textInputLayoutDriverCode.error = null
            }

            if (it.reviewMessageError != null) {
                binding.textInputLayoutReviewMessage.error = getString(it.reviewMessageError!!)
            } else {
                binding.textInputLayoutReviewMessage.error = null
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
            driverReviewViewModel.setDriverReview(it)
        }

        binding.message.afterTextChanged {
            driverReviewViewModel.setReviewMessage(it)
        }

        binding.submit.setOnClickListener {
            lifecycleScope.launch {
                driverReviewViewModel.insertDriverReview()
            }
        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun onImageClicked() {
        val fragment = ImagePickerBottomsheet()
        fragment.show(childFragmentManager, "String")
    }


    override fun returnString(item: Uri?) {
        ImageUtils.convertImagePathToBitmap(uri = item)?.let {
            driverReviewViewModel.setImage(it)
            binding.image.setImageBitmap(it)
        }
    }

    override fun onItemClick(item: String?) {
        imagePicker = ImagePickerActivityClass(
            context = requireContext(),
            this,
            requireActivity().activityResultRegistry,
            fragment = this,
        )
        imagePicker.cropOptions(true)

        when (item) {
            bottomSheetActionGallary -> {
                imagePicker.choosePhotoFromGallery()
            }

            bottomSheetActionCamera -> {
                imagePicker.takePhotoFromCamera()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker.onActivityResult(requestCode, resultCode, data)
    }

}