package com.englizya.profile_settings

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.common.utils.ImageUtils
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.User
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

    private var _formValidity = MutableLiveData<ProfileSettingFormState>()
    val formValidity: LiveData<ProfileSettingFormState> = _formValidity

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
    private fun checkFormValidity() {
        if (name.value == "") {
            _formValidity.postValue(ProfileSettingFormState(amountRes = R.string.name_is_required))
        } else {
            _formValidity.postValue(ProfileSettingFormState(formIsValid = true))
        }
    }
      fun updateUser() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        userRepository
            .updateUser(dataStore.getToken(), name.value, _address.value, ImageUtils.convertBitmapToFile(_image.value))
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
        Log.d("name" , _name.value.toString())
        checkFormValidity()
    }

    fun setAddress(address: String) {
        _address.value = address
    }
}