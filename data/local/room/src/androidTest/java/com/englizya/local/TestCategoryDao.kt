package com.englizya.local

import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest

class TestCategoryDao : KoinTest {

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                arrayListOf()
            )
        }
    }

    @Test
    fun getCategories() {
    }
}