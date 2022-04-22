package com.englizya.user_data

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.englizya.common.base.BaseFragment
import com.englizya.navigation.home.HomeActivity
import com.englizya.user_data.databinding.FragmentUserFormBinding
import com.opensooq.supernova.gligar.GligarPicker
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserFormFragment : BaseFragment() {

    private val IMAGE_REQ_CODE = 3004
    private val userFormViewModel: UserFormViewModel by viewModel()
    private lateinit var bind: FragmentUserFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        bind = FragmentUserFormBinding.inflate(layoutInflater)

        return bind.root
    }

    override fun onResume() {
        super.onResume()

        restoreValues()
    }

    private fun restoreValues() {
//        TODO : get place name by lat lng
//        bind.address.setText(userFormViewModel.address.value)
        bind.profileImage.setImageBitmap(userFormViewModel.profileImage.value)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        userFormViewModel.loading.observe(viewLifecycleOwner) {
            handleLoading(it)
        }

        userFormViewModel.error.observe(viewLifecycleOwner) {
            handleFailure(exception = it)
        }

        userFormViewModel.operationState.observe(viewLifecycleOwner) { state ->
            if (state) {
                progressToHome()
            }
        }
    }

    private fun setupListeners() {
        bind.address.setOnClickListener {
            progressToSelectLocation()
        }

        bind.profileImage.setOnClickListener {
            selectImage()
        }

        bind.add.setOnClickListener {
            progressToAddPaymentCard()
        }

        bind.save.setOnClickListener {
            userFormViewModel.saveUserData()
            bind.save.isEnabled = false
        }
    }

    private fun progressToSelectLocation() {

    }

    private fun progressToAddPaymentCard() {

    }

    private fun selectImage() {
        GligarPicker()
            .requestCode(IMAGE_REQ_CODE)
            .limit(1)
            .withFragment(this)
            .show()
    }

    private fun progressToHome() {
        activity?.startActivity(
            Intent(context, HomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && data.data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQ_CODE) {
            val imageBitmap = BitmapFactory.decodeFile(data.data!!.path)
            imageBitmap?.let {
                userFormViewModel.setProfileImage(imageBitmap)
            }
        }
    }
}