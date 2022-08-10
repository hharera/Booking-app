package com.englizya.announcement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.englizya.common.base.BaseViewModel
import com.englizya.model.model.Announcement
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.utils.Resource

class AnnouncementViewModel constructor(
    private val announcementRepository: AnnouncementRepository,
) : BaseViewModel() {

    fun getAnnouncement(announcementId: Int, forceOnline : Boolean = false) =
        announcementRepository
            .getAnnouncement(announcementId, forceOnline)
            .asLiveData()

    fun getAnnouncements(forceOnline : Boolean = false) =
        announcementRepository
            .getAllAnnouncement(forceOnline)
            .asLiveData()
}
