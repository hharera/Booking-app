package com.englizya.profile_settings

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.ImageUtils
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.User
import com.englizya.model.request.UserEditRequest
import com.englizya.model.response.UserEditResponse
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileSettingsViewModel constructor(
    private val userRepository: UserRepository,
    private val dataStore: UserDataStore,

    ) : BaseViewModel() {
    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private var _userEditResponse = MutableLiveData<UserEditResponse>()
    val userEditResponse: LiveData<UserEditResponse> = _userEditResponse

    private val _image: MutableLiveData<Bitmap> = MutableLiveData()
    val image: LiveData<Bitmap> = _image

    private val _name: MutableLiveData<String> = MutableLiveData()
    val name: LiveData<String> = _name

    private val _address: MutableLiveData<String> = MutableLiveData()
    val address: LiveData<String> = _address

    init {
        fetchUser()
    }

    private fun fetchUser() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        userRepository
            .getUser(dataStore.getToken())
            .onSuccess {

                updateLoading(false)
                _user.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    suspend fun updateUser() {
        updateLoading(true)
        createEditUserRequest()
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
            .onSuccess {
                updateLoading(false)
                updateUser(it)
            }

    }

    private fun createEditUserRequest() = kotlin.runCatching {
        UserEditRequest(
            name = name.value!!,
            address = address.value!!,
            image = ImageUtils.convertBitmapToFile(image.value!!),
        )
    }

    private fun updateUser(request: UserEditRequest) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        userRepository
            .updateUser(dataStore.getToken(), request)
            .onSuccess {
                updateLoading(false)
                _userEditResponse.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun setImage(bitmap: Bitmap) {
        _image.postValue(bitmap)
    }

    fun setName(name: String) {
        _name.value = name
    }

    fun setAddress(address: String) {
        _address.value = address
    }
}