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
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.OfferRepository
import com.englizya.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val userRepository: UserRepository,
    private val offerRepository: OfferRepository,
    private val announcementRepository: AnnouncementRepository,
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
        getUser()
        getOffers()
        getAnnouncements()
    }

    private fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        userRepository
            .getUser(dataStore.getToken())
            .onSuccess {
                userDatabase.getMarketDao().insertUser(it)
                _user.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    fun getOffers() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        offerRepository
            .getAllOffers()
            .onSuccess {
                updateLoading(false)
                _offers.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun getAnnouncements() = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        announcementRepository
            .getAllAnnouncement()
            .onSuccess {
                updateLoading(false)
                _announcements.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }
}
