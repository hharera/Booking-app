package com.englizya.complaint

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
import com.englizya.complaint.databinding.FragmentComplaintBinding
import com.englizya.complaint.util.ImageUtils
import com.opensooq.supernova.gligar.GligarPicker
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComplaintFragment : BaseFragment() {

    companion object {
        private const val IMAGE_REQ_CODE = 3005
    }

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
        val view = FragmentComplaintBinding.inflate(layoutInflater)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        complaintViewModel.insertionCompleted.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    showDoneDialog()
                }
            }
        }

        complaintViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.submit.isEnabled = it.formIsValid

            if (it.descriptionError == null) {
                binding.desc.error = getString(it.descriptionError!!)
            } else if (it.titleError == null) {
                binding.title.error = getString(it.titleError!!)
            }
        }
    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.image.setOnClickListener {
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
        binding.image.setImageBitmap(complaintViewModel.image.value)
        binding.title.setText(complaintViewModel.title.value)
    }

    private fun onImageClicked() {
        GligarPicker()
            .requestCode(IMAGE_REQ_CODE)
            .limit(1)
            .withFragment(this)
            .show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = ImageUtils.convertImagePathToBitmap(data)
            imageBitmap?.let {
                complaintViewModel.setImage(it)
            }
        }
    }

}