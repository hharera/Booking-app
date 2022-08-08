package com.englizya.announcement

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.repository.*
import io.mockk.coEvery
import io.mockk.mockk
import io.philippeboisney.common_test.data.mockAnnouncement
import kotlinx.coroutines.flow.flow
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
class AnnouncementFragmentTest : KoinTest {

    private val announcementRepository = mockk<AnnouncementRepository>()
    private val announcementViewModel: AnnouncementViewModel by inject()

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel {
                    AnnouncementViewModel(
                        announcementRepository,
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
        coEvery { announcementRepository.getAllAnnouncement() } returns flow { mockAnnouncement }
        launchFragment()

//        Espresso.onView(withId(R.id.source))
//            .perform(ViewActions.typeText("source"))
//            .check(ViewAssertions.matches(ViewMatchers.withText("source")))
//
//
//        Espresso.onView(withId(R.id.destination))
//            .perform(ViewActions.typeText("destination"))
//            .check(ViewAssertions.matches(ViewMatchers.withText("destination")))

    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<AnnouncementsFragment>()
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}