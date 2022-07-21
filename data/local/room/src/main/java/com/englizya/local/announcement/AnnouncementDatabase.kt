package com.englizya.local.announcement

import androidx.room.Database
import androidx.room.RoomDatabase
import com.englizya.model.model.Announcement

@Database(
    version = 1,
    entities = [Announcement::class],
    exportSchema = true,


    )
abstract class AnnouncementDatabase : RoomDatabase(){
    abstract fun getMarketDao() : AnnouncementDao

}