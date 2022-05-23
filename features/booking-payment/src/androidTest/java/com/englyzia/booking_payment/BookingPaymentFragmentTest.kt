package com.englyzia.booking_payment


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
import com.englizya.repository.PaymentRepository
import com.englizya.repository.ReservationRepository
import com.englizya.repository.UserRepository
import com.englyzia.booking.BookingViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.philippeboisney.common_test.data.TestUser
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
class BookingPaymentFragmentTest : KoinTest {

    private val userRepository = mockk<UserRepository>()
    private val paymentRepository = mockk<PaymentRepository>()
    private val reservationRepository = mockk<ReservationRepository>()
    private val userDataStore = mockk<UserDataStore>()
    private val bookingViewModel = mockk<BookingViewModel>()


    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel {
                    BookingViewModel(
                        mockk(),
                        mockk(),
                        mockk(),
                        mockk(),
                        mockk(),
                        mockk(),
                    )
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
        coEvery { bookingViewModel.submitBooking() } returns Unit
        coEvery { bookingViewModel.whenPayButtonClicked() } returns Unit
        coEvery { bookingViewModel.setSelectedPaymentMethod(any()) } returns Unit
        coEvery { bookingViewModel.setTransactionRef(any()) } returns Unit
        coEvery { bookingViewModel.confirmReservation() } returns Unit

        launchFragment()

        Espresso.onView(withId(R.id.cardMethodCL))
            .perform(ViewActions.click())

        Espresso.onView(withText(R.id.englizyaWalletCL))
            .perform(ViewActions.click())
    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<BookingPaymentFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}