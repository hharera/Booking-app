package com.englizya.repository.impl

import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.model.request.ComplaintRequest
import com.englizya.repository.SupportRepository
import com.englizya.repository.di.repositoryModule
import com.google.common.truth.Truth
import com.englizya.firebase.di.firebaseModule
import com.englizya.firebase.di.firebaseServiceModule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

internal class SupportRepositoryTest : KoinTest, KoinComponent {

    private val supportRepository: SupportRepository by inject()

    companion object{
        const val token : String = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSenVNWFNLVEdjTm9jNkx3ZDZzdlBuSzczRTQyIiwiZXhwIjoxNjUwNzQ3OTk0LCJpYXQiOjE2NTA2NjE1OTR9.03fB-giDjaah4HwQ-5NMQOltMdReKV5MPaevDrnW7Ag"
    }

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                arrayListOf(
                    clientModule,
                    remoteModule,
                    firebaseModule,
                    firebaseServiceModule,
                    repositoryModule
                )
            )
        }
    }

    @Test
    fun checkInsertionComplaint() = runTest {
        launch {
            supportRepository.insertComplaint(
                ComplaintRequest(
                    complaintDesc = "Desc",
                    complaintTitle = "Desc",
                ),
                token
            ).getOrElse {
                it.printStackTrace()
                null
            }.let {
                Truth.assertThat(it).isNotNull()
            }
        }
    }
}