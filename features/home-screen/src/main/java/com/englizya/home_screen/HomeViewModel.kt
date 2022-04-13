package com.englizya.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Announcement
import com.englizya.model.model.Offer
import com.englizya.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    private var _onNavigationClicked = MutableLiveData<Boolean>(false)
    val onNavigationClicked: LiveData<Boolean> = _onNavigationClicked

    private var _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> = _offers

    private var _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements

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
}
