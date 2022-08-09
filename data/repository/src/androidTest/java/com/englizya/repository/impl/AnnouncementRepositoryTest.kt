package com.englizya.repository.impl

import androidx.lifecycle.asLiveData
import androidx.room.withTransaction
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.api.AnnouncementService
import com.englizya.local.announcement.AnnouncementDao
import com.englizya.local.announcement.AnnouncementDatabase
import com.englizya.repository.AnnouncementRepository
import com.englizya.repository.utils.Resource
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.philippeboisney.common_test.data.announcementId
import io.philippeboisney.common_test.data.mockAnnouncement
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import io.mockk.mockk


@RunWith(AndroidJUnit4::class)
internal class AnnouncementRepositoryTest : KoinTest {

    private val announcementRepository : AnnouncementRepository by inject()
    private val announcementDao = mockk<AnnouncementDao>()
    private val announcementDatabase = mockk<AnnouncementDatabase>()
    private val announcementService = mockk<AnnouncementService>()

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                single<AnnouncementRepository> {
                    AnnouncementRepositoryImpl(
                        announcementService,
                        announcementDatabase,
                        announcementDao
                    )
                }
            }
        )
    }


    @Test
    fun test() {
        coEvery { announcementDatabase.withTransaction {  } } returns Unit
        coEvery { announcementDao.getAnnouncement(any()) } returns flow{ mockAnnouncement }
        coEvery { announcementService.getAnnouncement(any()) } returns mockAnnouncement

        announcementRepository.getAnnouncement(announcementId.toString()).also {
            Truth.assertThat(it.asLiveData().value).isInstanceOf(Resource.Success::class.java)
        }
    }
}
