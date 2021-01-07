package com.alexandra.musicapp.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.alexandra.musicapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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

        val bottomNavigationItemView = onView(withId(R.id.fragment_favorite_music))
        bottomNavigationItemView.perform(click())

        val bottomNavigationItemView2 = onView(withId(R.id.fragment_music_catalog))
        bottomNavigationItemView2.perform(click())

        val viewGroup = onView(withId(R.id.app_logo))
        viewGroup.check(matches(isDisplayed()))

        val recyclerView = onView(withId(R.id.shimmer_catalog))
        recyclerView.check(matches(isDisplayed()))
        recyclerView.check(RecyclerViewItemCountAssertion(0))
    }
}

class RecyclerViewItemCountAssertion(expectedCount: Int) : ViewAssertion {
    private val matcher: Matcher<Int?>? = not(expectedCount)

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }
        val recyclerView = view as RecyclerView?
        val adapter = recyclerView!!.adapter!!
        assertThat(adapter.itemCount, matcher)
    }
}
