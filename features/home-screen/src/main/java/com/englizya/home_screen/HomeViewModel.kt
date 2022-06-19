package com.englizya.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
import com.englizya.local.UserDatabase
import com.englizya.model.model.Announcement
import com.englizya.model.model.Offer
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel constructor(
    private val userRepository: UserRepository,
    private val dataStore: UserDataStore,
    private val userDatabase: UserDatabase,
) : BaseViewModel() {


    private var _onNavigationClicked = MutableLiveData<Boolean>(false)
    val onNavigationClicked: LiveData<Boolean> = _onNavigationClicked

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private var _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        viewModelScope.launch(Dispatchers.IO) {

            fetchUser()
        }
    }


    private suspend fun fetchUser() {
        userRepository
            .fetchUser(dataStore.getToken())
            .onSuccess {

                  userDatabase.getMarketDao().insertUser(it)



                _user.postValue(it)

            }
            .onFailure {
                handleException(it)
            }
    }

    fun getOffers(offers: List<Offer>) {
        _offers.value = offers
    }

    fun getAnnouncements(announcements: List<Announcement>) {
        _announcements.value = announcements

    }

    fun onNavigationClicked() {
        _onNavigationClicked.postValue(true)
//        _onNavigationClicked.postValue(false)
    }

    fun getUserName(): String =
        dataStore.getUserName()
}
