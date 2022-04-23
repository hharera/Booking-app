package com.englyzia.reviewdriver

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.englizya.datastore.UserDataStore
import com.englizya.repository.SupportRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockInjector
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class DriverReviewViewModelTest : TestCase(), KoinTest {


    @RelaxedMockK
    private lateinit var supportRepository: SupportRepository

    @RelaxedMockK
    private lateinit var userDataStore: UserDataStore

    private val driverReviewModule = module {
        viewModel {
            DriverReviewViewModel(supportRepository, userDataStore)
        }
    }

    @Before
    fun setup(): Unit {
        MockKAnnotations.init(this)
        loadKoinModules(
            arrayListOf(
                driverReviewModule
            )
        )
    }




}