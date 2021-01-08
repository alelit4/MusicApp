package com.alexandra.musicapp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
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
class AlbumsBehaviourShould {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun load_albums_of_an_artist() {
        val searchButton = onView(withId(R.id.search_button))
        searchButton.perform(click())

        val searchAutoComplete = onView(withId(R.id.search_src_text))
        searchAutoComplete.perform(replaceText("oreja"), closeSoftKeyboard())
        val searchAutoComplete2 = onView(withId(R.id.search_src_text))
        searchAutoComplete2.perform(pressImeActionButton())

        thread {
            Thread.sleep(4000)
        }.run()

        val constraintLayoutCatalog = onView(withId(R.id.shimmer_catalog))
        constraintLayoutCatalog.perform(click())

        thread {
            Thread.sleep(4000)
        }.run()

        val constraintLayoutAlbums = onView(withId(R.id.shimmer_albums))
        constraintLayoutAlbums.perform(click())

        constraintLayoutAlbums.check(ViewAssertions.matches(isDisplayed()))
        constraintLayoutAlbums.check(RecyclerViewItemCountAssertion(0))
    }

}
