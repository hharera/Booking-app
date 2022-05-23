package com.englizya.complaint

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
import com.englizya.complaint.databinding.FragmentComplaintBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComplaintFragment : BaseFragment(), ImagePickerActivityClass.OnResult,
    ImagePickerBottomsheet.ItemClickListener {

    companion object {
        private const val IMAGE_REQ_CODE = 3005
    }

    private lateinit var imagePicker: ImagePickerActivityClass
    private lateinit var binding: FragmentComplaintBinding
    private val complaintViewModel: ComplaintViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentComplaintBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        complaintViewModel.image.observe(viewLifecycleOwner) {
            binding.imageView.setImageBitmap(it)
        }

        complaintViewModel.insertionCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showDoneDialog()
                    findNavController().popBackStack()
                }
            }
        }

        complaintViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.submit.isEnabled = it.formIsValid

            if (it.descriptionError != null) {
                binding.textInputLayoutDesc.error = getString(it.descriptionError!!)
            } else {
                binding.textInputLayoutDesc.error = null
            }

            if (it.titleError != null) {
                binding.textInputLayoutTitle.error = getString(it.titleError!!)
            } else {
                binding.textInputLayoutTitle.error = null
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imageView.setOnClickListener {
            onImageClicked()
        }

        binding.desc.afterTextChanged {
            complaintViewModel.setDescription(it)
        }

        binding.title.afterTextChanged {
            complaintViewModel.setTitle(it)
        }

        binding.submit.setOnClickListener {
            lifecycleScope.launch {
                complaintViewModel.insertComplaint()
            }
        }

        binding.cancel.setOnClickListener {
            findNavController().popBackStack()
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
        binding.desc.setText(complaintViewModel.desc.value)
        binding.imageView.setImageBitmap(complaintViewModel.image.value)
        binding.title.setText(complaintViewModel.title.value)
    }

    private fun onImageClicked() {
        val fragment = ImagePickerBottomsheet()
        fragment.show(childFragmentManager, "String")
    }


    override fun returnString(item: Uri?) {
        ImageUtils.convertImagePathToBitmap(uri = item)?.let {
            complaintViewModel.setImage(it)
            binding.imageView.setImageBitmap(it)
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