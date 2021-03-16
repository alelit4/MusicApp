package com.alexandra.musicapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.alexandra.musicapp.R
import com.alexandra.musicapp.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class CatalogBehaviourShould {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun load_catalog_and_maintain_state() {
        val appCompatImageView = onView(withId(R.id.search_button))
        appCompatImageView.perform(click())

        val searchAutoComplete = onView(withId(R.id.search_src_text))

        searchAutoComplete.perform(replaceText("oreja de van gogh"), closeSoftKeyboard())
        val searchGoButton = onView(withId(R.id.search_go_btn))
        searchGoButton.perform(click())

        thread {
            Thread.sleep(1000)
        }.run()

        val bottomNavigationItemView = onView(withId(R.id.fragment_favorite_music))
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(withId(R.id.fragment_music_catalog))
        bottomNavigationItemView2.perform(click())

        val recyclerView = onView(withId(R.id.shimmer_catalog))
        recyclerView.check(matches(isDisplayed()))
        recyclerView.check(RecyclerViewItemCountAssertion(0))
    }
}

