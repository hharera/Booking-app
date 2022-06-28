package com.englizya.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.OfferRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnouncementViewModel constructor(
    private val announcementRepository: AnnouncementRepository,
): BaseViewModel() {

    private var _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements
    init {
        viewModelScope.launch(Dispatchers.IO) {


            getAnnouncements()
        }
    }
    fun getAnnouncements() = viewModelScope.launch {
        updateLoading(true)
        announcementRepository
            .getAllAnnouncement()
            .onSuccess {
                updateLoading(false)
                _announcements.value = it
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }
}