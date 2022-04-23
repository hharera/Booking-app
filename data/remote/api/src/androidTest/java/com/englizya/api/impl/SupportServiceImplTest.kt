package com.englizya.api.impl

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.englizya.api.SupportService
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.englizya.model.request.ComplaintRequest
import com.englizya.model.request.DriverReviewRequest
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4ClassRunner::class)
internal class SupportServiceImplTest : KoinTest {

    private val token: String =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSenVNWFNLVEdjTm9jNkx3ZDZzdlBuSzczRTQyIiwiZXhwIjoxNjUwODA2MDY2LCJpYXQiOjE2NTA3MTk2NjZ9.HjODM9Fn3JEij-NSBCnYQsNH9jNh4MkKl_Z0iy9XiL8"
    private val supportService: SupportService by inject()

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                arrayListOf(
                    clientModule,
                    remoteModule
                )
            )
        }
    }

    @Test
    fun insertComplaint() = runTest {
        runBlocking {
            supportService.insertComplaint(
                ComplaintRequest(
                    "title, title, title",
                    "title, title, title"
                ),
                "Bearer token"
            )
        }.let {
            Truth.assertThat(it).isNotNull()
        }
    }

    @Test
    fun insertDriverReview() {
        runBlocking {
            supportService.insertDriverReview(
                DriverReviewRequest(
                    review = 4,
                    driverCode = 805,
                    complaintImage = null,
                    reviewMessage = null
                ), token
            )
        }.let {
            Truth.assertThat(it).isNotNull()
        }
    }
}