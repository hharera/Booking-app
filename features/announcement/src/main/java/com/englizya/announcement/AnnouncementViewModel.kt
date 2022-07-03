package com.englizya.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.englizya.common.base.BaseViewModel
import com.englizya.local.announcement.AnnouncementDatabase
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnnouncementViewModel constructor(
    private val announcementRepository: AnnouncementRepository,
    private val announcementDatabase: AnnouncementDatabase,
) : BaseViewModel() {

    private var _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements

    private var _announcementsDetails = MutableLiveData<Announcement>()
    val announcementsDetails: LiveData<Announcement> = _announcementsDetails


    private val _announcementsId = MutableLiveData<String?>()
    val announcementsId: MutableLiveData<String?>
        get() = _announcementsId


    fun getAnnouncements(forceOnline : Boolean) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        announcementRepository
            .getAllAnnouncement(forceOnline)
            .onSuccess {
                updateLoading(false)
//                viewModelScope.launch(Dispatchers.IO) {
//                    announcementDatabase.getMarketDao().insertAnnouncements(it)
//                }
                _announcements.postValue(it)
            }
            .onFailure {
                updateLoading(false)
                handleException(it)
            }
    }

    fun getAnnouncementDetails(announcementId: String?) = viewModelScope.launch(Dispatchers.IO) {
        updateLoading(true)
        if (announcementId != null) {
            announcementRepository
                .getAnnouncementDetails(announcementId)
                .onSuccess {
                    updateLoading(false)
                    _announcementsDetails.postValue(it)
                }
                .onFailure {
                    updateLoading(false)
                    handleException(it)
                }
        }
    }
}