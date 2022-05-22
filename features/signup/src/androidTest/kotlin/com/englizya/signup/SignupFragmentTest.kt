package com.englizya.signup

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class SignupFragmentTest : KoinTest {

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel {
                    SignupViewModel()
                }
            }
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun test_to_show_accepted_when_click() {
        launchFragment()

        Espresso.onView(withId(R.id.accept_policy))
            .perform(ViewActions.click())
    }

    @Test
    fun test_to_accept_phone_number() {
        launchFragment()

        Espresso.onView(withId(R.id.phoneNumber))
            .perform(ViewActions.typeText("01062227714"))

        Espresso.onView(withId(R.id.signup))
            .check(ViewAssertions.matches(isEnabled()))

        Espresso.onView(withId(R.id.phoneNumber))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("01262227714"))

        Espresso.onView(withId(R.id.signup))
            .check(ViewAssertions.matches(isEnabled()))

        Espresso.onView(withId(R.id.phoneNumber))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("01562227714"))

        Espresso.onView(withId(R.id.signup))
            .check(ViewAssertions.matches(isEnabled()))

        Espresso.onView(withId(R.id.phoneNumber))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("01162227714"))

        Espresso.onView(withId(R.id.signup))
            .check(ViewAssertions.matches(isEnabled()))

        Espresso.onView(withId(R.id.phoneNumber))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("011062227714"))

        Espresso.onView(withId(R.id.signup))
            .check(ViewAssertions.matches(isDisplayed()))

        Espresso.onView(withId(R.id.phoneNumber))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("01227714"))

        Espresso.onView(withId(R.id.signup))
            .check(ViewAssertions.matches(isDisplayed()))
    }


    @Test
    fun test_to_accept_phone_numbers() {
        launchFragment()

        Espresso.onView(withId(R.id.accept_policy))
            .perform(ViewActions.click())
    }

    @Test
    fun test_to_show_trips_with_2_elements() {

    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<SignupFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}