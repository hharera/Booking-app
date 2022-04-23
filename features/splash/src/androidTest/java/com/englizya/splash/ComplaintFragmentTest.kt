package com.englizya.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.englizya.complaint.ComplaintFragment
import com.englizya.home_screen.HomeViewModel
import com.englizya.profile.ProfileFragment
import com.englizya.profile.ProfileViewModel
import com.englizya.select_service.SelectServiceActivity
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class ComplaintFragmentTest : KoinTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var splashViewModel: SplashViewModel

    @RelaxedMockK
    private lateinit var homeViewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var profileViewModel: ProfileViewModel

    private val testModule = module {
        viewModel {
            splashViewModel
        }

        viewModel {
            homeViewModel
        }

        viewModel {
            profileViewModel
        }
    }


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        startKoin {
            loadKoinModules(
                listOf(testModule)
            )
        }
    }

    @Test
    fun validateIntentSentToPackage() {
        ActivityScenario
            .launch(SplashActivity::class.java)

        ActivityScenario
            .launch(SelectServiceActivity::class.java)

        FragmentScenario.launch(ProfileFragment::class.java)
        FragmentScenario.launch(ComplaintFragment::class.java)

        Espresso
            .onView(ViewMatchers.withId(R.id.title))
            .perform(ViewActions.typeText("Title Title Title "))
    }

    @Test
    fun listGoesOverTheFold() {
    }
}