package com.englizya.profile


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.datastore.UserDataStore
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.philippeboisney.common_test.data.TestUser
import io.philippeboisney.common_test.data.TestUserBalance
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class ProfileViewModelTest : KoinTest {

    private val userRepository = mockk<UserRepository>()
    private val walletRepository = mockk<WalletRepository>()
    private val userDataStore = mockk<UserDataStore>()
    private val profileViewModel: ProfileViewModel by inject()

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel {
                    ProfileViewModel(userRepository, walletRepository, userDataStore)
                }
            }
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun test_to_accept_phone_number() {
        coEvery { userDataStore.getToken() } returns "token"
        coEvery { userRepository.fetchUser(any()) } returns kotlin.runCatching { TestUser.user }
        coEvery { walletRepository.getBalance(any()) } returns kotlin.runCatching { TestUserBalance.userBalance }
    }

}