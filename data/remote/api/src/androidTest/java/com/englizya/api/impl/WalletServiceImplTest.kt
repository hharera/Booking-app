package com.englizya.api.impl

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.englizya.api.WalletService
import com.englizya.api.di.clientModule
import com.englizya.api.di.remoteModule
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(AndroidJUnit4ClassRunner::class)
class WalletServiceImplTest : KoinTest {

    private val token: String =
        "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJSenVNWFNLVEdjTm9jNkx3ZDZzdlBuSzczRTQyIiwiZXhwIjoxNjUwODA2MDY2LCJpYXQiOjE2NTA3MTk2NjZ9.HjODM9Fn3JEij-NSBCnYQsNH9jNh4MkKl_Z0iy9XiL8"
    private val walletService : WalletService by inject()

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
    fun insertDriverReview() {
        runBlocking {
            walletService.requestRecharge(
                token
            )
        }.let {
            Truth.assertThat(it).isNotNull()
        }
    }
}