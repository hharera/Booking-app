package com.englizya.booking

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.datastore.UserDataStore
import com.englizya.repository.*
import com.englyzia.booking.BookingViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.philippeboisney.common_test.datasets.TripDataset.TRIP_LIST
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class BookingFragmentTest : KoinTest {

    private val stationRepository = mockk<StationRepository>()
    private val tripRepository = mockk<TripRepository>()
    private val userRepository = mockk<UserRepository>()
    private val datastore = mockk<UserDataStore>()
    private val reservationRepository = mockk<ReservationRepository>()
    private val paymentRepository = mockk<PaymentRepository>()

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel {
                    BookingViewModel(
                        stationRepository,
                        tripRepository,
                        userRepository,
                        datastore,
                        reservationRepository,
                        paymentRepository
                    )
                }
            }
        )
    }

    fun tearDown() {
        stopKoin()
    }


    @Test
    fun test_to_show_app_bar() {
        coEvery { tripRepository.searchTrips(any()) } returns kotlin.runCatching { TRIP_LIST }
        launchFragment()


        Espresso.onView(withId(R.id.source))
            .perform(ViewActions.typeText("source"))
            .check(ViewAssertions.matches(ViewMatchers.withText("source")))


        Espresso.onView(withId(R.id.destination))
            .perform(ViewActions.typeText("destination"))
            .check(ViewAssertions.matches(ViewMatchers.withText("destination")))

    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<BookingFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}