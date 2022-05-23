package com.englizya.terms_conditions

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.mockk
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.util.*


@RunWith(AndroidJUnit4::class)
class RefundFragmentTest : KoinTest {

    @Before
    fun setUp() {
        loadKoinModules(
            module {

            }
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun test_to_accept_phone_number() {
        launchFragment()

        Locale.setDefault(Locale.forLanguageTag("ar"))


        Espresso.onView(withId(R.id.refund_policy_details))
            .check(ViewAssertions.matches(isDisplayed()))
            .check(ViewAssertions.matches(ViewMatchers.withText("Refund Policy")))
    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<RefundFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}