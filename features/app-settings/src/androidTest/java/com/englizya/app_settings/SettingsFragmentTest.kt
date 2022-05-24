package com.englizya.app_settings

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.datastore.UserDataStore
import io.mockk.coEvery
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
class SettingsFragmentTest : KoinTest {

    private val userDataStore: UserDataStore = mockk()

    @Before
    fun setUp() {
        loadKoinModules(
            module {
                viewModel {
                    SettingsViewModel(userDataStore)
                }
            }
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }


    @Test
    fun test_to_change_ui_when_select_arabic() {
        coEvery { userDataStore.getLanguage() } returns "en"
        coEvery { userDataStore.setLanguage(any()) } returns Unit
        launchFragment()

        Espresso.onView(withId(R.id.arabic))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.arabicRadioButton))
            .check(ViewAssertions.matches(isChecked()))

        Espresso.onView(withId(R.id.englishRadioButton))
            .check(ViewAssertions.matches(isNotChecked()))

        Espresso.onView(withId(R.id.save))
            .perform(ViewActions.click())
    }

    @Test
    fun test_to_change_ui_when_select_english() {
        coEvery { userDataStore.getLanguage() } returns "ar"
        coEvery { userDataStore.setLanguage(any()) } returns Unit
        launchFragment()


        Espresso.onView(withId(R.id.english))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.englishRadioButton))
            .check(ViewAssertions.matches(isChecked()))

        Espresso.onView(withId(R.id.arabicRadioButton))
            .check(ViewAssertions.matches(isNotChecked()))

        Espresso.onView(withId(R.id.save))
            .perform(ViewActions.click())
    }

    private fun launchFragment(): NavController {
        val mockNavController = mockk<NavController>(relaxed = true)
        val homeScenario =
            launchFragmentInContainer<SettingsFragment>(themeResId = R.style.AppStyle)
        homeScenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        return mockNavController
    }

}