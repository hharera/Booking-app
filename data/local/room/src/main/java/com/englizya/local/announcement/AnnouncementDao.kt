package com.englizya.local.announcement

import androidx.room.*
import com.englizya.model.model.Announcement

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnnouncements(announcement: List<Announcement>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnnouncement(announcement: Announcement)

    @Query(value = "SELECT * from Announcement ")
    fun getAnnouncements(): List<Announcement>

    @Query(value = "SELECT * from Announcement where announcementId = :announcementId limit 1")
    fun getAnnouncement(announcementId: String): Announcement

    @Query(value = "DELETE from Announcement")
    fun clearAnnouncements()
}