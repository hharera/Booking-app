package com.englizya.local.announcement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.englizya.model.model.Announcement

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnnouncements(announcement: List<Announcement>)

    @Query(value = "SELECT * from Announcement ")
    fun getAnnouncements(): List<Announcement>

    @Query(value = "SELECT * from Announcement where announcementId = :announcementId limit 1")
    fun getAnnouncement(announcementId: String): Announcement
}