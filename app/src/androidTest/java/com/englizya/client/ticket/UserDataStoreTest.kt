package com.englizya.client.ticket

import com.englizya.datastore.di.dataStoreModule
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject


class UserDataStoreTest : KoinTest {

    private val userDataStore: UserDataStore by inject()

//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        printLogger()
//        modules()
//    }

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(
                dataStoreModule
            )
        }
    }

    @Test
    fun testSetToken() {
        assertThat(
            userDataStore.getLanguage()
        ).isNull()
    }

    fun testGetToken() {}

    fun testSetLanguage() {}

    fun testGetLanguage() {}
}