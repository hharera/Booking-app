package com.englizya.profile_settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.app.imagepickerlibrary.ImagePickerActivityClass
import com.app.imagepickerlibrary.ImagePickerBottomsheet
import com.app.imagepickerlibrary.bottomSheetActionCamera
import com.app.imagepickerlibrary.bottomSheetActionGallary
import com.englizya.common.base.BaseFragment
import com.englizya.common.extension.afterTextChanged
import com.englizya.common.utils.ImageUtils
import com.englizya.model.model.User
import com.englizya.profile_settings.databinding.FragmentProfileSettingsBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.compose.get

class ProfileSettingsFragment : BaseFragment(), ImagePickerActivityClass.OnResult,
    ImagePickerBottomsheet.ItemClickListener {

    companion object {
        private const val IMAGE_REQ_CODE = 3005
    }

    private lateinit var imagePicker: ImagePickerActivityClass
    private lateinit var binding: FragmentProfileSettingsBinding
    private val profileSettingViewModel: ProfileSettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentProfileSettingsBinding.inflate(layoutInflater)
        changeStatusBarColor(R.color.grey_100)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        profileSettingViewModel.user.observe(viewLifecycleOwner) {
            updateUI(it)
        }
        profileSettingViewModel.userEditResponse.observe(viewLifecycleOwner) {
            Log.d("Response", it.toString())
            if (it != null) {
                showToast(it.message)
                findNavController().popBackStack()
            }
        }
        profileSettingViewModel.image.observe(viewLifecycleOwner) {
            Log.d("Image", ImageUtils.convertBitmapToFile(it).toString())
        }
        profileSettingViewModel.formValidity.observe(viewLifecycleOwner) {
            binding.save.isEnabled = it.formIsValid
            if (it.amountRes != null) {
                if (it.amountRes == R.string.name_is_required) {
                    binding.textInputLayoutName.error = getString(R.string.name_is_required)

                }

            } else {
                binding.textInputLayoutName.error = null

            }
        }
    }


    @OptIn(ExperimentalCoilApi::class)
    private fun updateUI(user: User?) {
        if (user != null) {
            binding.name.setText(user.name)
            binding.address.setText(user.address)

            binding.profileImage.setContent {
                SuggestionImage(user.imageUrl, profileSettingViewModel)

            }
        } else {
            binding.profileImage.setContent {
                SuggestionImage(null, profileSettingViewModel)
            }
        }

    }

    private fun setupListeners() {
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.name.afterTextChanged {
            Log.d("NAme", it)
            profileSettingViewModel.setName(it)
        }

        binding.address.afterTextChanged {
            Log.d("address", it)

            profileSettingViewModel.setAddress(it)
        }
        binding.save.setOnClickListener {
            lifecycleScope.launch {
                profileSettingViewModel.updateUser()

            }
        }
    }

    override fun returnString(item: Uri?) {
        ImageUtils.convertImagePathToBitmap(uri = item)?.let {
            profileSettingViewModel.setImage(it)
//            binding.imageView.setImageBitmap(it)
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

@ExperimentalCoilApi
@Composable
fun SuggestionImage(
    userImageUrl: String?,
    profileSettingsViewModel: ProfileSettingsViewModel,
    coilLoader: CoilLoader = get()
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = ImageUtils.getImageFromUri(imageUri, context = context)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    if (bitmap != null) {
        profileSettingsViewModel.setImage(bitmap)
        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = null,

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clickable {
                    launcher.launch("image/*")
                }
        )
    } else {
        if (userImageUrl != null) {
            Image(
                painter = rememberImagePainter(coilLoader.imageRequest(userImageUrl)),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable {
                        launcher.launch("image/*")
                    },
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_profile_user),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable {
                        launcher.launch("image/*")
                    },
            )
        }

    }

}