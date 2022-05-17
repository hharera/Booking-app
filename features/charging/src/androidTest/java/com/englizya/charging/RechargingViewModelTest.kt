package com.englizya.charging

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.collectLatest
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RechargingViewModelTest {

    private val viewModel = RechargingViewModel()

    @Test
    suspend fun test() {
        viewModel.setAmount("100")

        viewModel.formValidity.collectLatest {
            it?.formIsValid?.let { it1 -> assertThat(it1).isTrue() }
        }
    }

}