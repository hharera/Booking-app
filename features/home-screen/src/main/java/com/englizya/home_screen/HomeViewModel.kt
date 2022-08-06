package com.englizya.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.datastore.UserDataStore
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
) : BaseViewModel() {


    private var _onNavigationClicked = MutableLiveData<Boolean>(false)
    val onNavigationClicked: LiveData<Boolean> = _onNavigationClicked

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    val announcements = announcementRepository.getAllAnnouncement().asLiveData()

    private var _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        getUser()

    }

    private fun getUser() = viewModelScope.launch(Dispatchers.IO) {
        userRepository
            .getUser(dataStore.getToken(),true)
            .onSuccess {
                _user.postValue(it)
            }
            .onFailure {
                handleException(it)
            }
    }

    fun getOffers(forceOnline : Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        offerRepository
            .getAllOffers(forceOnline)
            .onSuccess {
                updateLoading(false)
                _offers.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

//    fun getAnnouncements(forceOnline : Boolean) = viewModelScope.launch(Dispatchers.IO) {
//        updateLoading(true)
//        announcementRepository
//            .getAllAnnouncement(forceOnline)
//            .onSuccess {
//                updateLoading(false)
//                _announcements.postValue(it)
//            }
//            .onFailure {
//                updateLoading(false)
//                handleException(it)
//            }
//    }
}
