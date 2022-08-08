package com.englizya.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AnnouncementViewModel constructor(
    private val announcementRepository: AnnouncementRepository,
) : BaseViewModel() {

    private val _announcement = MutableLiveData<Resource<Announcement>>()
    val announcement: LiveData<Resource<Announcement>> = _announcement

    val announcements = announcementRepository.getAllAnnouncement().asLiveData()

    fun getAnnouncement(announcementId: Int) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)

        announcementRepository
            .getAnnouncement(announcementId)
            .onEach {
                _announcement.postValue(it)
            }
    }
}