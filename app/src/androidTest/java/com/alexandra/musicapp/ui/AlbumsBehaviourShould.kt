package com.alexandra.musicapp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
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
class AlbumsBehaviourShould {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun load_albums_of_an_artist() {
        val searchButton = onView(withId(R.id.search_button))
        searchButton.perform(click())

        val searchAutoComplete = onView(withId(R.id.search_src_text))
        searchAutoComplete.perform(replaceText("oreja de van gogh"), closeSoftKeyboard())

        thread {
            Thread.sleep(1000)
        }.run()

        val searchAutoComplete2 = onView(withId(R.id.search_src_text))
        searchAutoComplete2.perform(pressImeActionButton())

        thread {
            Thread.sleep(1000)
        }.run()

        val constraintLayoutArtist = onView(withId(R.id.row_layout_artist))
        constraintLayoutArtist.perform(click())

        thread {
            Thread.sleep(1000)
        }.run()

        val constraintLayoutAlbums = onView(withId(R.id.shimmer_albums))
        constraintLayoutAlbums.check(ViewAssertions.matches(isDisplayed()))
        constraintLayoutAlbums.check(RecyclerViewItemCountAssertion(16))
    }
}
