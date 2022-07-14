package com.englizya.profile_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileSettingsViewModel constructor(
    private val userRepository: UserRepository,
    private val dataStore: UserDataStore,

    ): BaseViewModel() {
    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
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
}