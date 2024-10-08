package com.englizya.local.announcement

import androidx.room.*
import com.englizya.model.model.Announcement
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnnouncements(announcement: List<Announcement>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnnouncement(announcement: Announcement)

    @Query(value = "SELECT * from Announcement ")
    fun getAnnouncements():Flow<List<Announcement>>

    @Query(value = "SELECT * from Announcement where announcementId = :announcementId limit 1")
    fun getAnnouncement(announcementId: Int): Flow<Announcement>

    @Query(value = "DELETE from Announcement")
    fun clearAnnouncements()

    @Delete
    fun deleteAnnouncement(announcement : Announcement)
}