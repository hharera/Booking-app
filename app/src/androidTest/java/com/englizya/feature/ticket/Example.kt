package com.englizya.app.ticket

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.englizya.navigation.signup.SignupActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
internal  class Example {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignupActivity::class.java)


    @Test
    fun useAppContext() {

        onView(withId(R.id.phoneNumber))
            .check(matches(withText("")))
    }
}