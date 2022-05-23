package com.englizya.profile


import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.datastore.UserDataStore
import com.englizya.model.dto.UserBalance
import com.englizya.model.model.User
import com.englizya.repository.UserRepository
import com.englizya.repository.WalletRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.philippeboisney.common_test.data.TestUser
import io.philippeboisney.common_test.data.TestUserBalance
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.*


@RunWith(AndroidJUnit4::class)
class ProfileFragmentTest : KoinTest {

    private val userRepository = mockk<UserRepository>()
    private val walletRepository = mockk<WalletRepository>()
    private val userDataStore = mockk<UserDataStore>()
    private val profileViewModel = mockk<ProfileViewModel>()


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
        coEvery { profileViewModel.user } returns MutableLiveData(TestUser.user)
        coEvery { profileViewModel.userBalance } returns MutableStateFlow(1555.00)

        launchFragment()

        Espresso.onView(withText(R.string.refund_policy))
            .check(ViewAssertions.matches(isDisplayed()))
            .perform(ViewActions.click())

        Espresso.onView(withText(R.string.refund_policy))
            .check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(withSubstring("Refund ")))
    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<ProfileFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}