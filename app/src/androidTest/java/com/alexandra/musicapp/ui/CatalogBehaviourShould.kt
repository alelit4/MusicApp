package com.alexandra.musicapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.alexandra.musicapp.R
import com.alexandra.musicapp.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.concurrent.thread

@LargeTest
@RunWith(AndroidJUnit4::class)
class CatalogBehaviourShould {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun load_catalog_and_maintain_state() {
        val appCompatImageView = onView(withId(R.id.search_button))
        appCompatImageView.perform(click())

        val searchAutoComplete = onView(withId(R.id.search_src_text))

        searchAutoComplete.perform(replaceText("oreja"), closeSoftKeyboard())
        val searchGoButton = onView(withId(R.id.search_go_btn))
        searchGoButton.perform(click())

        thread {
            Thread.sleep(4000)
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

