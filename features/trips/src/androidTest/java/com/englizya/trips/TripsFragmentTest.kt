package com.englizya.trips

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.repository.TripRepository
import com.englizya.trips.di.tripsModule
import io.mockk.coEvery
import io.mockk.mockk
import io.philippeboisney.common_test.datasets.TripDataset.TRIP_LIST
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class TripsFragmentTest : KoinTest {

    private val tripRepository = mockk<TripRepository>()

    fun setUp() {
        startKoin {
            module {
                single { tripRepository }
            }

            loadKoinModules(bookingModule)
        }
    }

    fun tearDown() {
        stopKoin()
    }


    @Test
    fun test_to_show_app_bar() {
        coEvery { tripRepository.searchTrips(any()) } returns kotlin.runCatching { TRIP_LIST }
        launchFragment()


        Espresso.onView(withId(R.id.from))
            .check(ViewAssertions.matches(withText("Lorem ipsum")))

        Espresso.onView(withId(R.id.from))
            .check(ViewAssertions.matches(withText("Lorem ipsum")))
    }

    @Test
    fun test_to_show_trips_with_2_elements() {
        coEvery { tripRepository.searchTrips(any()) } returns kotlin.runCatching { TRIP_LIST }
        launchFragment()

        Espresso.onView(withId(R.id.trips))
            .perform(ViewActions.swipeDown())

        Espresso.onView(withId(R.id.trips))
            .perform(ViewActions.scrollTo())

    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<TripsFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}