package com.englizya.user_data

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.repository.UserRepository
import com.google.type.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserFormViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _location = MutableLiveData<LatLng>()
    val address: LiveData<LatLng> = _location

    private var _profileImage = MutableLiveData<Bitmap>()
    val profileImage: LiveData<Bitmap> = _profileImage

    private var _operationState = MutableLiveData<Boolean>()
    val operationState: LiveData<Boolean> = _operationState

    fun setLocation(location: LatLng) {
        _location.postValue(location)
    }

    fun setProfileImage(imageBitmap: Bitmap) {
        _profileImage.value = imageBitmap
    }

    fun saveUserData() {
//        TODO : save payments
    }
}