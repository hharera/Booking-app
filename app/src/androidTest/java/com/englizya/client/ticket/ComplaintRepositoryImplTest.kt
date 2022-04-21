package com.englizya.client.ticket

import com.englizya.api.SupportService
import com.englizya.api.di.clientModule
import com.englizya.model.request.ComplaintRequest
import com.englizya.repository.di.repositoryModule
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject
import org.koin.test.KoinTest
import org.koin.test.inject

internal class ComplaintRepositoryImplTest : KoinTest {

    private val complaintService: SupportService by inject()

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                arrayListOf(
                    clientModule,
                    repositoryModule
                )
            )
        }
    }

    @Test
    suspend fun checkInsertionComplaint(): Unit {
        complaintService.insertComplaint(
            ComplaintRequest(
                complaintDesc = "Desc",
                complaintTitle = "Desc",
            )
        ).let {

        }
    }
}